package com.buildcart.app.modules.signuothree.`data`.model

import com.buildcart.app.R
import com.buildcart.app.appcomponents.di.MyApp
import kotlin.String

data class SignUoThreeModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtHelpFAQs: String? = MyApp.getInstance().resources.getString(R.string.lbl_help_faq_s2)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtFrequentlyAske: String? =
      MyApp.getInstance().resources.getString(R.string.msg_frequently_aske)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguage: String? = MyApp.getInstance().resources.getString(R.string.msg_how_to_place_o)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtDescription: String? =
      MyApp.getInstance().resources.getString(R.string.msg_norem_ipsum_dol)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguageOne: String? = MyApp.getInstance().resources.getString(R.string.msg_how_to_place_o)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguageTwo: String? = MyApp.getInstance().resources.getString(R.string.msg_how_to_place_o)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguageThree: String? =
      MyApp.getInstance().resources.getString(R.string.msg_how_to_place_o)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguageFour: String? =
      MyApp.getInstance().resources.getString(R.string.msg_how_to_place_o)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguageFive: String? =
      MyApp.getInstance().resources.getString(R.string.msg_how_to_place_o)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguageSix: String? = MyApp.getInstance().resources.getString(R.string.msg_got_any_other)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtHelplineNumber: String? =
      MyApp.getInstance().resources.getString(R.string.msg_helpline_number)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtMailIDqecur: String? = MyApp.getInstance().resources.getString(R.string.msg_mail_id_qecur)

)
