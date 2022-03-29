package com.example.stylonicproject

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment()
{
    private lateinit var username_reg:EditText
    private lateinit var password_reg :EditText
    private lateinit var conpassword_reg :EditText
    private lateinit var fAuth:FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       var view = inflater.inflate(R.layout.fragment_register, container, false)

        username_reg = view.findViewById(R.id.username_reg)
        password_reg =view.findViewById(R.id.password_reg)
        conpassword_reg=view.findViewById(R.id.conpassword_reg)
        fAuth=Firebase.auth

        view.findViewById<TextView>(R.id.textView_reg).setOnClickListener {
            var navRegister = activity as  FragmentNavigation
            navRegister.navigationFlag(LoginFragment(),false)

        }
        view.findViewById<Button>(R.id.btn_register_reg).setOnClickListener {
            validateEmptyForm()
        }
        return view
    }
        private fun firebaseSignUp(){
            btn_register_reg.isEnabled = false
            btn_register_reg.alpha = 0.5f
            fAuth.createUserWithEmailAndPassword(username_reg.text.toString(),
                password_reg.text.toString()).addOnCompleteListener{
                    task->
                if (task.isSuccessful){
                    //Toast.makeText(context, "Register Successfully !", Toast.LENGTH_SHORT).show()
                    var navHome = activity as FragmentNavigation
                    navHome.navigationFlag(HomeFragment(),addToStack = true)
                }
                 else{
                    btn_register_reg.isEnabled = true
                    btn_register_reg.alpha = 1.0f
                    Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun validateEmptyForm(){
            val icon = AppCompatResources.getDrawable(requireContext(),
            R.drawable.ic_baseline_gpp_maybe_24)
            icon?.setBounds(0,0,icon.intrinsicWidth,icon.intrinsicHeight)
            when{
                TextUtils.isEmpty(username_reg.text.toString().trim())->{
                    username_reg.setError("Please enter username ",icon)
                }
                TextUtils.isEmpty(password_reg.text.toString().trim())->{
                    password_reg.setError("Please enter Password ",icon)
                }
                TextUtils.isEmpty(conpassword_reg.text.toString().trim())->{
                    conpassword_reg.setError("Please enter Password Again ",icon)
                }
                username_reg.text.toString().isNotEmpty() &&
                        password_reg.text.toString().isNotEmpty() &&
                        conpassword_reg.text.toString().isNotEmpty()->
                {
                    if(username_reg.text.toString().matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))) {
                        if (password_reg.text.toString().length >= 5) {

                            if (password_reg.text.toString() == conpassword_reg.text.toString()) {

                                firebaseSignUp()
                              //  Toast.makeText(context, "Register Successfully !", Toast.LENGTH_SHORT).show()
                            } else {
                                conpassword_reg.setError("Password not match")
                            }
                        }else {
                            password_reg.setError("Please Enter Atleast 5 Characters", icon)
                        }
                    }
                    else{
                        username_reg.setError("Please Enter Valid Email_Id",icon)
                        }
                        }
                }
            }
        }