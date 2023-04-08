package com.ubpis.inventame.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.squareup.picasso.Picasso;
import com.ubpis.inventame.R;
import com.ubpis.inventame.data.model.User;

import java.util.ArrayList;

public class UserCardAdapter extends RecyclerView.Adapter<UserCardAdapter.ViewHolder> {

    /** Definició de listener (interficie)
     *  per a quan algú vulgui escoltar un event de OnClickHide, és a dir,
     *  quan l'usuari faci clic en la creu (amagar) algún dels items de la RecyclerView
     */
    public interface OnClickHideListener {
        void OnClickHide(int position);
    }

    private ArrayList<User> mUsers; // Referència a la llista d'usuaris
    private OnClickHideListener mOnClickHideListener; // Qui hagi de repintar la ReciclerView
                                                      // quan s'amagui
    // Constructor
    public UserCardAdapter(ArrayList<User> userList) {
        this.mUsers = userList; // no fa new (La llista la manté el ViewModel)

    }

    public void setOnClickHideListener(OnClickHideListener listener) {
        this.mOnClickHideListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Inflate crea una view genèrica definida pel layout que l'hi passem (l'user_card_layout)
        View view = inflater.inflate(R.layout.user_card_layout, parent, false);

        // La classe ViewHolder farà de pont entre la classe User del model i la view (UserCard).
        return new ViewHolder(view);
    }

    /* Mètode cridat per cada ViewHolder de la RecyclerView */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // El ViewHolder té el mètode que s'encarrega de llegir els atributs del User (1r parametre),
        // i assignar-los a les variables del ViewHolder.
        // Qualsevol listener que volguem posar a un item, ha d'entrar com a paràmetre extra (2n).
        holder.bind(mUsers.get(position), this.mOnClickHideListener);
    }

    /**
     * Retorna el número d'elements a la llista.
     * @return
     */
    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    /**
     * Mètode que seteja de nou la llista d'usuaris si s'hi han fet canvis de manera externa.
     * @param users
     */
    public void setUsers(ArrayList<User> users) {
        this.mUsers = users; // no recicla/repinta res
    }

    /**
     * Mètode que repinta la RecyclerView sencera.
     */
    public void updateUsers() {
        notifyDataSetChanged();
    }

    /**
     * Mètode que repinta només posició indicada
     * @param position
     */
    public void hideUser(int position) {
        notifyItemRemoved(position);
    }

    /**
     * Classe ViewHolder. No és més que un placeholder de la vista (user_card_list.xml)
     * dels items de la RecyclerView. Podem implementar-ho fora de RecyclerViewAdapter,
     * però es pot fer dins.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mCardPictureUrl;
        private final TextView mCardFullName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardPictureUrl = itemView.findViewById(R.id.employee_image);
            mCardFullName = itemView.findViewById(R.id.employee_name);
        }

        public void bind(final User user, OnClickHideListener listener) {
            mCardFullName.setText(user.getName() + " " + user.getSurname());
            // Carrega foto de l'usuari de la llista directament des d'una Url
            // d'Internet.
            //Picasso.get().load(user.getmPictureURL()).into(mCardPictureUrl);
            // Seteja el listener onClick del botó d'amagar (hide), que alhora
            // cridi el mètode OnClickHide que implementen els nostres propis
            // listeners de tipus OnClickHideListener.

        }
    }

}