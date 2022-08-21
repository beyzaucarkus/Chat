package com.example.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class chatrecycleradapter :RecyclerView.Adapter<chatrecycleradapter.chatholder>()
{ private val VIEW_TYPE_MESSAGE_SENT=1
    private val     VIEW_TYPE_MESSAGE_RECEIVED=2
    class chatholder(itemView: View):RecyclerView.ViewHolder(itemView)
    { }


private val diffUtil=object :DiffUtil.ItemCallback<chat>(){
    override fun areItemsTheSame(oldItem: chat, newItem: chat): Boolean {
        return oldItem==newItem }

    override fun areContentsTheSame(oldItem: chat, newItem: chat): Boolean {
        return oldItem==newItem }
}
private val recyclerlistdiffer=AsyncListDiffer(this,diffUtil)

    var chats:List<chat>
    get()=recyclerlistdiffer.currentList
    set(value)=recyclerlistdiffer.submitList(value)

    override fun getItemViewType(position: Int): Int {

        val chat =chats.get(position)
        if(chat.user==FirebaseAuth.getInstance().currentUser?.email.toString()){
            return VIEW_TYPE_MESSAGE_SENT
        }else{
return  VIEW_TYPE_MESSAGE_RECEIVED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chatholder {
        if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler, parent, false)
            return chatholder(view)
        }else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler2, parent, false)
            return chatholder(view)
        }
    }

    override fun onBindViewHolder(holder: chatholder, position: Int) {
val textView=holder.itemView.findViewById<TextView>(R.id.chatrecyclertext)
        textView.text="${chats.get(position).user}: ${chats.get(position).text}"
    }

    override fun getItemCount(): Int {
return chats.size
    }






}