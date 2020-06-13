package com.example.myapplication

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.fragment.app.Fragment

class OverviewFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.overview_text,container,false)
        val textView = view.findViewById<TextView>(R.id.info)

        textView.movementMethod = ScrollingMovementMethod()

        return view
    }
}

class PreventionFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.prevention_text,container,false)
        val textView = view.findViewById<TextView>(R.id.info)

        textView.movementMethod = ScrollingMovementMethod()

        return view
    }
}

class SymptomsFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.symptoms_text,container,false)
        val textView = view.findViewById<TextView>(R.id.info)

        textView.movementMethod = ScrollingMovementMethod()

        return view
    }
}