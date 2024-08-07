package com.buildcart.app.modules.mycart.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.buildcart.app.R
import com.buildcart.app.appcomponents.base.BaseFragment
import com.buildcart.app.data.ProfileDataResponse
import com.buildcart.app.data.SessionManager
import com.buildcart.app.data.response.BuyAllProductsRequest
import com.buildcart.app.databinding.FragmentMyCartBinding
import com.buildcart.app.modules.frame311.ui.Frame311Activity
import com.buildcart.app.modules.homeonecontainer.ui.HomeOneContainerActivity
import com.buildcart.app.modules.mycart.data.adapter.CartAdapter
import com.buildcart.app.modules.mycart.data.model.CartProduct
import com.buildcart.app.modules.mycart.data.model.CartProductItem
import com.buildcart.app.modules.mycart.data.model.CartRepository
import com.buildcart.app.modules.mycart.data.viewmodel.CartViewModelFactory
import com.buildcart.app.modules.mycart.`data`.viewmodel.MyCartVM
import com.buildcart.app.modules.responses.CompltedOrderResponse
import com.buildcart.app.modules.signuofifteen.ui.ProfileActivity
import com.buildcart.app.modules.signuofive.ui.SignUoFiveActivity
import com.buildcart.app.service.APIInterface
import com.buildcart.app.service.APIManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.String
import kotlin.Unit

class MyCartFragment : BaseFragment<FragmentMyCartBinding>(R.layout.fragment_my_cart) {
  private val cartRepository= CartRepository(APIManager.apiInterface)
  //private val viewModel: MyCartVM by viewModels<MyCartVM>()
  private val viewModelCart: MyCartVM by viewModels { CartViewModelFactory(cartRepository) }



  private lateinit var apiInterface: APIInterface


  private lateinit var sessionManager: SessionManager
  override fun onInitialized(): Unit {
    viewModelCart.navArguments = arguments

    sessionManager= SessionManager(requireActivity())
    apiInterface = APIManager.apiInterface
    binding.myCartVM = viewModelCart
   // viewModelCart.setSessionManager(SessionManager(requireContext()))
  // viewModelCart.getCartProducts()
//    val totalPrices = cartAdapter.calculateTotalPrices()
//
//    Log.e("Total Amount",totalPrices.toString())


    getProfiledata()

    getMyStudioRequests()


//    binding.btnProceedToPay.setOnClickListener {
//      buyproducts()
//    }

    binding.imageEllipseTwelve.setOnClickListener {
      val i=Intent(requireActivity(),ProfileActivity::class.java)
      startActivity(i)
    }

  }





  private fun getMyStudioRequests(){

    val serviceGenerator= APIManager.apiInterface
    val accessToken=sessionManager.fetchAuthToken()
    val authorization="Bearer $accessToken"
    val call=serviceGenerator.getallproducts(authorization)

    call.enqueue(object : retrofit2.Callback<CartProduct>{
      @SuppressLint("SetTextI18n")
      override fun onResponse(
        call: Call<CartProduct>,
        response: Response<CartProduct>
      ) {
        val customerResponse=response.body()

        if((customerResponse!=null)&&(customerResponse.success=="true")){
          val studioModel=customerResponse.response
          binding.recyclerView.apply {
            layoutManager=
              LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
            val adapter=CartNewAdapter(studioModel,sessionManager)
            binding.recyclerView.adapter=adapter
            binding.txtPriceThree.text=adapter.calculateTotalPrices().toString()
            binding.txtPriceFour.text=adapter.calculateTotalPrices().toString()
          }

        }
      }
      override fun onFailure(call: Call<CartProduct>, t: Throwable) {
        t.printStackTrace()
        Log.e("error", t.message.toString())
      }
    })
  }


  fun getProfiledata(){

    val accessToken=sessionManager.fetchAuthToken()

    val authorization="Bearer $accessToken"
    val call=apiInterface.getProfileDetails(authorization)
    call.enqueue(object : Callback<ProfileDataResponse> {
      override fun onResponse(call: Call<ProfileDataResponse>, response: Response<ProfileDataResponse>) {
        if (response.isSuccessful) {

          val loginResponse = response.body()
          if (loginResponse != null) {
            //Toast.makeText(this@ProfileActivity, "Profile Data Successfully Fetched", Toast.LENGTH_LONG).show()




            val file=APIManager.getImageUrl(loginResponse.response!!.photo.toString())




            Picasso.get().load(file).transform(ProfileActivity.CircleCrop()).placeholder(R.drawable.default_profile_background).into(binding.imageEllipseTwelve)


            //navigateToHomeActivity()
          } else {
            Toast.makeText(requireActivity(), "Profile Data fetching failed", Toast.LENGTH_SHORT).show()
          }
        } else {
          Toast.makeText(requireActivity(), "Profile Data fetching Failed", Toast.LENGTH_SHORT).show()
        }
      }
      override fun onFailure(call: Call<ProfileDataResponse>, t: Throwable) {
        Toast.makeText(requireActivity(), "Profile Data fetching: ${t.message}", Toast.LENGTH_SHORT).show()
      }
    })
  }
  override fun setUpClicks(): Unit {
    binding.linearRowplus.setOnClickListener {
      val destIntent = HomeOneContainerActivity.getIntent(requireActivity(), null)
      startActivity(destIntent)
      requireActivity().onBackPressed()
    }

    binding.imageVector.setOnClickListener {
      // Open Frame311Activity -MenuDrawer Activity when ImageVector is clicked
      val intent = Intent(activity, Frame311Activity::class.java)
      startActivity(intent)
    }

//    binding.btnProceedToPay.setOnClickListener {
//      val i=Intent(requireActivity(),SignUoFiveActivity::class.java)
//     // i.putExtra("total_amount",binding.txtPriceFour.text)
//      startActivity(i)
//    }


    binding.btnProceedToPay.setOnClickListener {
      val totalPrice = binding.txtPriceFour.text.toString().toDoubleOrNull()
      if (totalPrice == null || totalPrice == 0.0) {
        Toast.makeText(requireActivity(), "Please add items inside the cart", Toast.LENGTH_SHORT).show()
      } else {
        val i = Intent(requireActivity(), SignUoFiveActivity::class.java)
        // i.putExtra("total_amount", binding.txtPriceFour.text)
        startActivity(i)
      }
    }
  }



  companion object {
    const val TAG: String = "MY_CART_FRAGMENT"


    fun getInstance(bundle: Bundle?): MyCartFragment {
      val fragment = MyCartFragment()
      fragment.arguments = bundle
      return fragment
    }
  }
}
