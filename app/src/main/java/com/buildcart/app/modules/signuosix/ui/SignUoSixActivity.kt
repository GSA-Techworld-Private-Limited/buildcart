package com.buildcart.app.modules.signuosix.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.buildcart.app.R
import com.buildcart.app.appcomponents.base.BaseActivity
import com.buildcart.app.data.SessionManager
import com.buildcart.app.data.response.RequestSignUpResponse
import com.buildcart.app.databinding.ActivitySignUoSixBinding
import com.buildcart.app.modules.signuoone.ui.SignUoOneActivity
import com.buildcart.app.modules.signuosix.`data`.viewmodel.SignUoSixVM
import com.buildcart.app.modules.signuotwelve.ui.SignUoTwelveActivity
import com.buildcart.app.modules.signuotwo.ui.SignUoTwoActivity
import com.buildcart.app.service.APIInterface
import com.buildcart.app.service.APIManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.String
import kotlin.Unit

class SignUoSixActivity : BaseActivity<ActivitySignUoSixBinding>(R.layout.activity_sign_uo_six) {
  private val viewModel: SignUoSixVM by viewModels<SignUoSixVM>()


  private lateinit var apiInterface: APIInterface

  private var mobileNumber:String=""
  private var otp:String=""

  private lateinit var etMobileNumber:EditText



  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.signUoSixVM = viewModel

    window.statusBarColor= ContextCompat.getColor(this,R.color.gray_703)

    etMobileNumber=findViewById(R.id.txtEnterMobilenu)


    apiInterface = APIManager.apiInterface





  }

  override fun setUpClicks(): Unit {
    binding.txtSignup.setOnClickListener {
      val destIntent = SignUoOneActivity.getIntent(this, null)
      startActivity(destIntent)
    }
    binding.btnLogin.setOnClickListener {
//      val destIntent = SignUoTwelveActivity.getIntent(this, null)
//      startActivity(destIntent)

      mobileNumber=etMobileNumber.text.toString()
      if (mobileNumber.isBlank()){
        Toast.makeText(this@SignUoSixActivity, "Please Enter the Mobile Number", Toast.LENGTH_SHORT).show()}
      else{
      makeSignUpRequestOTP(mobileNumber)}

      binding.progressBar.visibility= View.VISIBLE
    }
  }

  fun makeSignUpRequestOTP(mobileNumber: String?) {
    val call=apiInterface.verifyLoginOTP(mobileNumber)
    call.enqueue(object : Callback<RequestSignUpResponse> {
      override fun onResponse(call: Call<RequestSignUpResponse>, response: Response<RequestSignUpResponse>) {
        if (response.isSuccessful) {
          binding.progressBar.visibility= View.GONE
          val loginResponse = response.body()
          if (loginResponse != null) {
            Toast.makeText(this@SignUoSixActivity, "Otp Sent Successfully: ${loginResponse.otp}", Toast.LENGTH_LONG).show()
            otp=loginResponse.otp.toString()
            navigateToOTPActivity()
          } else {
            Toast.makeText(this@SignUoSixActivity, "OTP Sending failed", Toast.LENGTH_SHORT).show()
            binding.progressBar.visibility= View.GONE
          }
        } else {
          Toast.makeText(this@SignUoSixActivity, "OTP Sending Failed", Toast.LENGTH_SHORT).show()
          binding.progressBar.visibility= View.GONE
        }
      }
      override fun onFailure(call: Call<RequestSignUpResponse>, t: Throwable) {
        Toast.makeText(this@SignUoSixActivity, "OTP Sending Failed: ${t.message}", Toast.LENGTH_SHORT).show()
        binding.progressBar.visibility= View.GONE
      }
    })
  }


  fun navigateToOTPActivity(){
    val destIntent = SignUoTwelveActivity.getIntent(this, null)
    destIntent.putExtra("otp",otp)
    destIntent.putExtra("mobile_number",mobileNumber)
    startActivity(destIntent)
    finish()
  }


  companion object {
    const val TAG: String = "SIGN_UO_SIX_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, SignUoSixActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
