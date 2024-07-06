package com.example.appmenurestaurantcliente.views.categoriasscreenview

import  android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appmenurestaurantcliente.R
import com.example.appmenurestaurantcliente.models.Categoria
import com.example.appmenurestaurantcliente.utils.Constantes

class AdaptadorCategorias (
    val context: Context,
    val listaCategorias: ArrayList<Categoria>,
    val onClick: OnItemClicked
): RecyclerView.Adapter<AdaptadorCategorias.ViewHolder>(){


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int):
            ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_categoria, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoria = listaCategorias[position]

        Glide
            .with(context)
            .load( "${Constantes.PATH_IMG_CATEGORIAS}${categoria.imagenCategoria}")
            .centerInside()
            .placeholder(R.drawable.icon_falta_foto)
            .into(holder.ivCategoria)

        holder.tvNomCategoria.text = categoria.nomCategoria.uppercase()
        holder.tvNomCategoria.setTextColor(context.resources.getColor(R.color.white))

        holder.cvCategoria.setCardBackgroundColor(context.resources.getColor(R.color.valle_dorado))
        holder.cvCategoria.setOnClickListener{
            onClick.verPlatillosCategoria(categoria.nomCategoria)
        }
    }

    override fun getItemCount(): Int {
        return listaCategorias.size
    }

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val cvCategoria =  itemView.findViewById(R.id.cvCategoria) as CardView
        val ivCategoria  =  itemView.findViewById(R.id.ivCategoria) as ImageView
        val tvNomCategoria =  itemView.findViewById(R.id.tvNomCategoria) as TextView
    }

    interface OnItemClicked{
        fun verPlatillosCategoria(nomCategoria: String)
    }

}