package com.codingblocks.msitnotes


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.semester_list.*
import kotlinx.android.synthetic.main.semester_list.view.*

class CseFragment : Fragment() {

    fun newInstance(someInt: Int): CseFragment {
        val myFragment = CseFragment()

        val args = Bundle()
        args.putInt("someInt", someInt)
        myFragment.setArguments(args)

        return myFragment
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

       val s= arguments!!.getInt("someInt",0)

        val fragmentView=inflater.inflate(R.layout.semester_list,container,false)

        val semesters= arrayOf(
                "SEM1","SEM2","SEM3","SEM4",
                "SEM5","SEM6","SEM7","SEM8"
        )

        val semesterAdapter=ArrayAdapter<String>(
                activity,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                semesters
        )

        fragmentView.lvSemester.adapter=semesterAdapter

        fragmentView.lvSemester.onItemClickListener = AdapterView.OnItemClickListener{parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            val semester=semesters[position];
            if(semester==semesters[0])
            {
//                val i=Intent(activity,CseSem1Uploads::class.java)
//                startActivity(i)
                val i=Intent(activity,viewUploads::class.java);
                i.putExtra("brase","CSE-sem1")
                i.putExtra("sl",s)
                startActivity(i)
            }
            else if(semester==semesters[1])
            {
                val i=Intent(activity,viewUploads::class.java);
                i.putExtra("brase","CSE-sem2")
                i.putExtra("sl",s)
                startActivity(i)
            }
            else if(semester==semesters[2])
            {
                val i=Intent(activity,viewUploads::class.java);
                i.putExtra("brase","CSE-sem3")
                i.putExtra("sl",s)
                startActivity(i)
            }
            else if(semester==semesters[3])
            {
                val i=Intent(activity,viewUploads::class.java);
                i.putExtra("brase","CSE-sem4")
                i.putExtra("sl",s)
                startActivity(i)
            }
            else if(semester==semesters[4])
            {
                //val i=Intent(activity,CseSem5Uploads::class.java)
                //startActivity(i)
                val i=Intent(activity,viewUploads::class.java);
                i.putExtra("brase","CSE-sem5")
                i.putExtra("sl",s)
                startActivity(i)
            }
            else if(semester==semesters[5])
            {
                val i=Intent(activity,viewUploads::class.java);
                i.putExtra("brase","CSE-sem6")
                i.putExtra("sl",s)
                startActivity(i)
            }
            else if(semester==semesters[6])
            {
                val i=Intent(activity,viewUploads::class.java);
                i.putExtra("brase","CSE-sem7")
                i.putExtra("sl",s)
                startActivity(i)
            }
            else if(semester==semesters[7])
            {
                val i=Intent(activity,viewUploads::class.java);
                i.putExtra("brase","CSE-sem8")
                i.putExtra("sl",s)
                startActivity(i)
            }
        }
        return fragmentView
        }
    }



