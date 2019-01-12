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

/**
 * A simple [Fragment] subclass.
 *
 */
class EceFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentView=inflater.inflate(R.layout.semester_list,container,false)
        val semesters= arrayOf(
                "SEM1","SEM2","SEM3","SEM4",
                "SEM5","SEM6","SEM7","SEM8"
        )

        val semesterAdapter= ArrayAdapter<String>(
                activity,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                semesters
        )

        fragmentView.lvSemester.adapter=semesterAdapter
        fragmentView.lvSemester.onItemClickListener = AdapterView.OnItemClickListener{ parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            val semester=semesters[position];
            if(semester==semesters[0])
            {
                val i=Intent(activity,viewUploads::class.java);
                i.putExtra("brase","ECE-sem1")
                startActivity(i)
            }
            else if(semester==semesters[1])
            {
                val i=Intent(activity,viewUploads::class.java);
                i.putExtra("brase","ECE-sem2")
                startActivity(i)
            }
            else if(semester==semesters[2])
            {
                val i=Intent(activity,viewUploads::class.java);
                i.putExtra("brase","ECE-sem3")
                startActivity(i)
            }
            else if(semester==semesters[3])
            {
                val i=Intent(activity,viewUploads::class.java);
                i.putExtra("brase","ECE-sem4")
                startActivity(i)
            }
            else if(semester==semesters[4])
            {
                val i=Intent(activity,viewUploads::class.java);
                i.putExtra("brase","ECE-sem5")
                startActivity(i)
            }
            else if(semester==semesters[5])
            {
                val i=Intent(activity,viewUploads::class.java);
                i.putExtra("brase","ECE-sem6")
                startActivity(i)
            }
            else if(semester==semesters[6])
            {
                val i=Intent(activity,viewUploads::class.java);
                i.putExtra("brase","ECE-sem7")
                startActivity(i)
            }
            else if(semester==semesters[7])
            {
                val i=Intent(activity,viewUploads::class.java);
                i.putExtra("brase","ECE-sem8")
                startActivity(i)
            }
        }
        return fragmentView
        }
    }

