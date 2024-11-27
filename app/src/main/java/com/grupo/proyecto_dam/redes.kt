package com.grupo.proyecto_dam

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class redes : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_redes, container, false)

        // Botones de redes sociales
        val btnLinkedIn: Button = view.findViewById(R.id.btn_linkedin)
        val btnInstagram: Button = view.findViewById(R.id.btn_instagram)
        val btnFacebook: Button = view.findViewById(R.id.btn_facebook)
        val btnTwitter: Button = view.findViewById(R.id.btn_twitter)
        val btnYouTube: Button = view.findViewById(R.id.btn_youtube)

        // Configurar los OnClickListeners para abrir las URLs
        btnLinkedIn.setOnClickListener {
            openUrl("https://www.linkedin.com/school/universidad-privada-del-norte/")
        }

        btnInstagram.setOnClickListener {
            openUrl("https://www.instagram.com/upn/")
        }

        btnFacebook.setOnClickListener {
            openUrl("https://www.facebook.com/UPN")
        }

        btnTwitter.setOnClickListener {
            openUrl("https://x.com/upn_oficial")
        }

        btnYouTube.setOnClickListener {
            openUrl("https://www.youtube.com/c/UniversidadPrivadadelNorteTV")
        }

        return view
    }

    // Función para abrir un URL en el navegador
    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
