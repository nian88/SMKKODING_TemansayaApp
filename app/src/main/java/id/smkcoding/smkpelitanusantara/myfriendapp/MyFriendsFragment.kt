package id.smkcoding.smkpelitanusantara.myfriendapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.my_friends_fragment.*
import kotlinx.android.synthetic.main.my_friends_item.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyFriendsFragment : Fragment() {

//    lateinit var listTeman : ArrayList<MyFriend>
    private var listTeman : List<MyFriend>? = null

    private var db: AppDatabase? = null
    private lateinit var adapter: MyFriendAdapter
    private var myFriendDao: MyFriendDao? = null
    companion object {
        fun newInstance(): MyFriendsFragment {
            return MyFriendsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_friends_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLocalDB()
        initView()
    }

    private fun initLocalDB() {
        db = AppDatabase.getAppDataBase(activity!!)
        myFriendDao = db?.myFriendDao()
    }

    private fun initView() {
        fabAddFriend.setOnClickListener { (activity as MainActivity).tampilMyFriendsAddFragment() }
//        simulasiDataTeman()
//        tampilTeman()
        ambilDataTeman()
    }
    private fun ambilDataTeman() {

//        listTeman = ArrayList()
        myFriendDao?.ambilSemuaTeman()?.observe(this, Observer { r ->

            listTeman = r

            when {
                listTeman?.size == 0 -> tampilToast("Belum ada data teman")

                else -> {
                    tampilTeman()
                }

            }

        })

    }

    private fun tampilToast(message: String) {
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }
    private fun tampilTeman() {
        listMyFriends.layoutManager = LinearLayoutManager(activity)
        adapter = MyFriendAdapter(activity!!, listTeman)
        listMyFriends.adapter = adapter
        adapter.setOnItemClickListener(object : MyFriendAdapter.ClickListener {
            override fun onClick(pos: Int, aView: View) {
                if (aView == ic_delete){
                    tampilToast(listTeman!!.get(pos).nama)
                    hapusDataTeman(listTeman!!.get(pos)!!.temanId!!.toLong())
                }else if (aView== ic_edit){
                    tampilToast(listTeman!!.get(pos).email)
                    (activity as MainActivity).tampilMyFriendsEditFragment(listTeman!!.get(pos)!!)
                }
            }
        })
    }
    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
    fun hapusDataTeman(temanId:Long?) : Job {
        return GlobalScope.launch {
            myFriendDao?.hapusTeman(temanId)
            (activity as MainActivity).tampilMyFriendsFragment()
        }

    }
}