package id.smkcoding.smkpelitanusantara.myfriendapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.R.attr.fragment



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tampilMyFriendsFragment()
    }

    private fun gantiFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment, frameId: Int
    ) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(frameId, fragment)

        transaction.commit()
    }

    fun tampilMyFriendsFragment() {
        gantiFragment(supportFragmentManager, MyFriendsFragment.newInstance(), R.id.contentFrame)
    }

    fun tampilMyFriendsAddFragment(){
        gantiFragment(supportFragmentManager, MyFriendsAddFragment.newInstance(), R.id.contentFrame)
    }
    fun tampilMyFriendsEditFragment(myFriend: MyFriend){
        val fragment = MyFriendsEditFragment.newInstance()
        val args = Bundle()
        args.putString("temanid", myFriend.temanId.toString())
        args.putString("nama", myFriend.nama)
        args.putString("kelamin", myFriend.kelamin)
        args.putString("email", myFriend.email)
        args.putString("telp", myFriend.telp)
        args.putString("alamat", myFriend.alamat)
        fragment.setArguments(args)
        gantiFragment(supportFragmentManager, fragment, R.id.contentFrame)
    }
}
