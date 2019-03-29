package id.smkcoding.smkpelitanusantara.myfriendapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.my_friends_item.*

class MyFriendAdapter(private val context: Context, private val items: List<MyFriend>?) :
    RecyclerView.Adapter<MyFriendAdapter.ViewHolder>() {

    lateinit var mClickListener: ClickListener
    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }
    interface ClickListener {
        fun onClick(pos: Int, aView: View)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_friends_item, parent, false),mClickListener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items!!.get(position))
    }

    override fun getItemCount(): Int = items!!.size

    class ViewHolder(override val containerView: View, val mClick:ClickListener) : RecyclerView.ViewHolder(containerView), LayoutContainer,View.OnClickListener {
        override fun onClick(v: View) {
            mClick.onClick(adapterPosition, v)
        }
        init {
            ic_edit.setOnClickListener(this)
            ic_delete.setOnClickListener(this)
        }
        fun bindItem(item: MyFriend) {
            txtFriendName.text = item.nama
            txtFriendEmail.text = item.email
            txtFriendTelp.text = item.telp
        }

    }
}