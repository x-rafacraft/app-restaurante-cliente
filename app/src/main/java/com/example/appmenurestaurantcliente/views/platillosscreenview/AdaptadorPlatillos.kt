package com.example.appmenurestaurantcliente.views.platillosscreenview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appmenurestaurantcliente.R
import com.example.appmenurestaurantcliente.models.Platillo
import com.example.appmenurestaurantcliente.views.categoriasscreenview.AdaptadorCategorias

class AdaptadorPlatillos(
    val listaPlatillos: ArrayList<Platillo>,
    val onClick: OnItemClicked
): RecyclerView.Adapter<AdaptadorPlatillos.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_platillo, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val platillo = listaPlatillos[position]

        holder.tvNombrePlatillo.text = platillo.nomPlatillo
        if (platillo.descPlatillo.isNullOrEmpty()) {
            holder.tvDescripcionPlatillo.visibility = View.GONE
        } else {
            holder.tvDescripcionPlatillo.visibility = View.VISIBLE
            holder.tvDescripcionPlatillo.text = platillo.descPlatillo
        }

        holder.tvPrecio.text = "S/ ${platillo.precio}"

        holder.ivAddLista.setOnClickListener {
            onClick.agregarPlatilloCuenta(platillo.nomPlatillo, platillo.precio)
        }

    }

    override fun getItemCount(): Int {
        return listaPlatillos.size
    }

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvNombrePlatillo = itemView.findViewById<TextView>(R.id.tvNombrePlatillo)
        val tvDescripcionPlatillo = itemView.findViewById<TextView>(R.id.tvDescripcionPlatillo)
        val tvPrecio = itemView.findViewById<TextView>(R.id.tvPrecio)
        val ivAddLista = itemView.findViewById<ImageView>(R.id.ivAddLista)
    }

    interface OnItemClicked {
        fun agregarPlatilloCuenta(nomPlatillo: String, precio: String)
    }

}