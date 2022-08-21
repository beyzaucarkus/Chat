package com.example.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat.databinding.FragmentChatfragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class chatfragment : Fragment() {
    private var _binding: FragmentChatfragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth:FirebaseAuth
    private lateinit var adapter: chatrecycleradapter
    private val chats = arrayListOf<chat>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestore=Firebase.firestore
        auth=Firebase.auth
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatfragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    { super.onViewCreated(view, savedInstanceState)

adapter= chatrecycleradapter()
binding.recyclerView.adapter=adapter
binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())

        binding.send.setOnClickListener {
            //tıklandığında
            auth.currentUser?.let {
                val user=it.email
                val enteredittext =binding.enteredittext.text.toString()
                val date=FieldValue.serverTimestamp()
                val dateMap=HashMap<String,Any>()
                dateMap.put("text",enteredittext)
                dateMap.put("user",user!!)
                dateMap.put("date",date)

                firestore.collection("Chats").add(dateMap).addOnSuccessListener {
                    binding.enteredittext.setText("")
                }.addOnFailureListener {
                    Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_LONG).show()
                    binding.enteredittext.setText("")
                }
            }
        }
        firestore.collection("Chats").orderBy("date",Query.Direction.ASCENDING).addSnapshotListener { value, error ->

            if(error != null){
                Toast.makeText(requireContext(),error.localizedMessage,Toast.LENGTH_LONG).show()
            }else{
                if(value !=null){
                    if(value.isEmpty)
                    {
                        Toast.makeText(requireContext(),"mesaj yok",Toast.LENGTH_LONG).show()
                    }else{
                        val documents=value.documents
                        chats.clear()
                        for(document in documents){
                            val text =document.get("text")as String
                            val user=document.get("user") as String
                           val chat=chat(user,text)
                            chats.add(chat)
                            adapter.chats=chats
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null }
}