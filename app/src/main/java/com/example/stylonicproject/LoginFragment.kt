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
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private lateinit var username_log: EditText
    private lateinit var password_log : EditText
    private lateinit var fAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_login, container, false)

        username_log = view.findViewById(R.id.username_log)
        password_log = view.findViewById(R.id.password_log)
        fAuth =Firebase.auth
0
        view.findViewById<TextView>(R.id.textView_log).setOnClickListener {
            var navRegister = activity as FragmentNavigation
            navRegister.navigationFlag(RegisterFragment(), false)
            }
            view.findViewById<Button>(R.id.btn_login).setOnClickListener {
                validateForm()
            }
            return view
        }

        private fun firebaseSignIn(){
            btn_login.isEnabled = false
            btn_login.alpha =0.5f
                fAuth.signInWithEmailAndPassword(username_log.text.toString(),
                password_log.text.toString()).addOnCompleteListener {
                task->
                    if (task.isSuccessful){
                        var navHome = activity as FragmentNavigation
                        navHome.navigationFlag(HomeFragment(),addToStack = true)

                    }else{
                        btn_login.isEnabled = false
                        btn_login.alpha = 1.0f
                        Toast.makeText(context,task.exception?.message,Toast.LENGTH_SHORT).show()
                    }
        }
        }
        private fun validateForm() {
            val icon = AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_gpp_maybe_24
            )
            icon?.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)
            when {
                TextUtils.isEmpty(username_log.text.toString().trim()) -> {
                    username_log.setError("Please enter username ", icon)
                }
                TextUtils.isEmpty(password_log.text.toString().trim()) -> {
                    password_log.setError("Please enter Password ", icon)
                }
                username_log.text.toString().isNotEmpty() &&
                        password_log.text.toString().isNotEmpty() -> {
                    if (username_log.text.toString().matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))) {

                        firebaseSignIn()
              //  Toast.makeText(context,"Login successfully !",Toast.LENGTH_SHORT).show()
                    } else {
                        username_log.setError("Please Enter Valid Email_Id", icon)
                    }
                }
            }
        }

}