package id.smkcoding.smkpelitanusantara.myfriendapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.my_friends_edit_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyFriendsEditFragment : Fragment() {
    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null
    private var namaInput : String = ""
    private var emailInput : String = ""
    private var telpInput : String = ""
    private var alamatInput : String = ""
    private var genderInput : String = ""


    companion object {
        fun newInstance(): MyFriendsEditFragment {
            return MyFriendsEditFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_friends_edit_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initLocalDB()
        edtName.setText(arguments!!.getString("nama"))
        edtTelp.setText(arguments!!.getString("telp"))
        edtEmail.setText(arguments!!.getString("email"))
        edtAddress.setText(arguments!!.getString("alamat"))
        if (arguments!!.getInt("kelamin").equals("perempuan")){
            spinnerGender.setSelection(2)
        }else{
            spinnerGender.setSelection(1)
        }
    }

    private fun initLocalDB() {
        db = AppDatabase.getAppDataBase(activity!!)
        myFriendDao = db?.myFriendDao()
    }

    private fun initView() {
        btnSave.setOnClickListener { validasiInput() }
        setDataSpinnerGener()
    }
    private fun setDataSpinnerGener() {
        val adapter = ArrayAdapter.createFromResource(activity!!,
            R.array.gender_list, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapter
    }
    private fun validasiInput() {
        namaInput = edtName.text.toString()
        emailInput = edtEmail.text.toString()
        telpInput = edtTelp.text.toString()
        alamatInput = edtAddress.text.toString()
        genderInput = spinnerGender.selectedItem.toString()

        when{
            namaInput.isEmpty() -> edtName.error = "Nama tidak boleh kosong"
            genderInput.equals("Pilih kelamin") -> tampilToast("Kelamin harus dipilih")
            emailInput.isEmpty() -> edtEmail.error = "Email tidak boleh kosong"
            telpInput.isEmpty() -> edtTelp.error = "Telp tidak boleh kosong"
            alamatInput.isEmpty() -> edtAddress.error = "Alamat tidak boleh kosong"

            else -> {

                val teman = MyFriend(arguments!!.getString("temanid").toInt(), nama = namaInput, kelamin = genderInput, email = emailInput, telp = telpInput, alamat = alamatInput)
                editDataTeman(teman)

            }
        }

    }

    private fun editDataTeman(teman: MyFriend) : Job {
        return GlobalScope.launch {
            myFriendDao?.updateTeman(teman.temanId!!.toLong(),teman.nama,teman.kelamin,teman.email,teman.telp,teman.alamat)
            (activity as MainActivity).tampilMyFriendsFragment()
        }
    }

    private fun tampilToast(message: String) {
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}