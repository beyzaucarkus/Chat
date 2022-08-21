package com.example.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.chat.databinding.FragmentLoginfragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Loginfragment : Fragment() {
    private var _binding: FragmentLoginfragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         auth = Firebase.auth
        val currenntuser=auth.currentUser
        if(currenntuser != null)
        {
            val action=LoginfragmentDirections.actionLoginfragmentToChatfragment()
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginfragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    { super.onViewCreated(view, savedInstanceState)

    binding.signup.setOnClickListener{
            // kullanıcı kayıt
  auth.createUserWithEmailAndPassword(binding.emailtext.text.toString(),binding.passwordtext.text.toString()).addOnSuccessListener {
      //kulanıcı oluştu
      val action=LoginfragmentDirections.actionLoginfragmentToChatfragment()
      findNavController().navigate(action)
  }.addOnFailureListener { exception->
      Toast.makeText( requireContext(),exception.localizedMessage, Toast.LENGTH_SHORT).show()
  } }


   binding.login.setOnClickListener {
            //tıklandığında giriş
       auth.signInWithEmailAndPassword(binding.emailtext.text.toString(),binding.passwordtext.text.toString()).addOnSuccessListener {
val action=LoginfragmentDirections.actionLoginfragmentToChatfragment()
           findNavController().navigate(action)
       }.addOnFailureListener {
           Toast.makeText(requireContext(),it.localizedMessage, Toast.LENGTH_SHORT).show()
       } }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null }
}