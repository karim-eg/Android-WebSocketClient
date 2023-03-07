package com.encept.websocket_client

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.encept.websocket_client.databinding.ActivityMainBinding
import org.java_websocket.handshake.ServerHandshake
import org.w3c.dom.Text
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

/*
Created By Encept Ltd (https://encept.co)
*/

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var webSocketClient: ChatWebSocketClient
    private var AL: ArrayList<HashMap<String, Any>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnSend = binding.btnSend
        val editMsg = binding.editMsg
        val listview1 = binding.listview1

        // load server url from strings.xml
        val serverUri = URI(getString(R.string.server_url))

        webSocketClient = ChatWebSocketClient(serverUri) { message ->
            // display incoming message in ListView
            runOnUiThread {
                run {
                    val _item = HashMap<String, Any>()
                    _item["message"] = message
                    AL.add(_item)
                }
                listview1.adapter = Listview1Adapter(AL)
            }
        }
        // connect to websocket server
        webSocketClient.connect()


        btnSend.setOnClickListener {
            try {
                // send message to websocket server
                webSocketClient.sendMessage(editMsg.text.toString())
                editMsg.setText("")
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // close websocket connection
        webSocketClient.close()
    }


    class Listview1Adapter(private val _data: ArrayList<HashMap<String, Any>>) : BaseAdapter() {
        override fun getCount(): Int {
            return _data.size
        }

        override fun getItem(_index: Int): HashMap<String, Any> {
            return _data[_index]
        }

        override fun getItemId(_index: Int): Long {
            return _index.toLong()
        }

        override fun getView(_position: Int, _v: View?, _container: ViewGroup?): View? {
            val _inflater = LayoutInflater.from(_container?.context)
            var _view = _v
            if (_view == null) {
                _view = _inflater.inflate(R.layout.cul, _container, false)
            }

            val text2 = _view?.findViewById<TextView>(R.id.text2)

            if (text2 != null) {
                text2.text = _data[_position]["message"].toString()
            }

            return _view
        }

    }

}