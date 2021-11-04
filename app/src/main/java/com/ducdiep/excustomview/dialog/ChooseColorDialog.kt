package com.ducdiep.excustomview.dialog

import android.app.Dialog
import android.content.ComponentCallbacks
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import com.ducdiep.excustomview.R
import com.ducdiep.excustomview.adapter.ColorAdapter
import kotlinx.android.synthetic.main.choose_color_dialog.*

class ChooseColorDialog(context: Context,style:Int):Dialog(context,style) {
    var onChoose:((String)->Unit)? = null
    fun setOnChooseItem(callback: (String)->Unit){
        onChoose=callback
    }
    lateinit var listColor:ArrayList<String>
    lateinit var adapter:ColorAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(R.layout.choose_color_dialog)

        addListColor()
        adapter = ColorAdapter(context,listColor)
        adapter.setOnClickItem {
            onChoose?.invoke(it)
            dismiss()
        }
        rcv_color.adapter = adapter
        btn_cancel.setOnClickListener {
            dismiss()
        }
    }
    private fun addListColor() {
        listColor = ArrayList()
        listColor.add("#FFFF00")
        listColor.add("#FCF75E")
        listColor.add("#FFF700")
        listColor.add("#FFEF00")
        listColor.add("#F7E98E")
        listColor.add("#FBEC5D")
        listColor.add("#FFDF00")
        listColor.add("#EEE600")
        listColor.add("#F0E130")
        listColor.add("#E6E200")
        listColor.add("#E4D96F")
        listColor.add("#ECD540")
        listColor.add("#CFCFC4")
        listColor.add("#FF8C00")
        listColor.add("#E49B0F")
        listColor.add("#ED9121")
        listColor.add("#FF7F00")
        listColor.add("#F28500")
        listColor.add("#ED872D")
        listColor.add("#FF7518")
        listColor.add("#C19A6B")
        listColor.add("#FF6700")
        listColor.add("#CD7F32")
        listColor.add("#CC7722")
        listColor.add("#D2691E")
        listColor.add("#B87333")
        listColor.add("#B0C4DE")
        listColor.add("#ADD8E6")
        listColor.add("#B0E0E6")
        listColor.add("#87CEFA")
        listColor.add("#87CEEB")
        listColor.add("#6495ED")
        listColor.add("#00BFFF")
        listColor.add("#1E90FF")
        listColor.add("#4169E1")
        listColor.add("#0000FF")
        listColor.add("#0000CD")
        listColor.add("#00008B")
        listColor.add("#000080")
        listColor.add("#32CD32")
        listColor.add("#98FB98")
        listColor.add("#90EE90")
        listColor.add("#00FA9A")
        listColor.add("#00FF7F")
        listColor.add("#3CB371")
        listColor.add("#2E8B57")
        listColor.add("#228B22")
        listColor.add("#008000")
        listColor.add("#9ACD32")
        listColor.add("#6B8E23")
        listColor.add("#556B2F")
        listColor.add("#FFFFFF")
        listColor.add("#FFFAFA")
        listColor.add("#F0FFF0")
        listColor.add("#F5FFFA")
        listColor.add("#080808")
        listColor.add("#000000")
        listColor.add("#101010")
        listColor.add("#606060")
        listColor.add("#FFD1DC")
        listColor.add("#FFC0CB")
        listColor.add("#FFB7C5")
        listColor.add("#FC8EAC")
        listColor.add("#E75480")
        listColor.add("#DE5D83")
        listColor.add("#800080")
        listColor.add("#9400D3")
        listColor.add("#9370DB")
        listColor.add("#7B68EE")
        listColor.add("#DEB887")
        listColor.add("#D2B48C")
        listColor.add("#BC8F8F")
        listColor.add("#B8860B")
        listColor.add("#FF0000")
        listColor.add("#FF5C5C")
        listColor.add("#CC0000")
    }
}