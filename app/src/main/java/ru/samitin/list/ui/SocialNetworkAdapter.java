package ru.samitin.list.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import ru.samitin.list.R;
import ru.samitin.list.data.CardData;
import ru.samitin.list.data.CardsSource;

public class SocialNetworkAdapter extends RecyclerView.Adapter<SocialNetworkAdapter.ViewHolder>{

    private final static String TAG = "SocialNetworkAdapter";
    private CardsSource cardsSource;
    private OnItemClickListener onItemClickListener;
    // Передаём в конструктор источник данных
    // В нашем случае это массив, но может быть и запрос к БД
    public SocialNetworkAdapter(CardsSource cardsSource){
        this.cardsSource=cardsSource;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    public interface OnItemClickListener{
        public void onItemClick(View view,int position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Создаём новый элемент пользовательского интерфейса
        // Через Inflater
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        Log.d(TAG, "onCreateViewHolder");

        // Здесь можно установить всякие параметры
        return new ViewHolder(view);
    }
    // Заменить данные в пользовательском интерфейсе
    // Вызывается менеджером
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Получить элемент из источника данных (БД, интернет...)
        // Вынести на экран, используя ViewHolder
        holder.setData(cardsSource.getCardData(position));
        Log.d(TAG, "onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return cardsSource.size();
    }

    // Этот класс хранит связь между данными и элементами View
    // Сложные данные могут потребовать несколько View на один пункт списка
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView description;
        private AppCompatImageView image;
        private CheckBox like;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           title=itemView.findViewById(R.id.title);
           description=itemView.findViewById(R.id.description);
           image=itemView.findViewById(R.id.imageView);
           like=itemView.findViewById(R.id.like);
            // Обработчик нажатий на этом ViewHolder
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener!=null)
                        onItemClickListener.onItemClick(view,getAdapterPosition());
                }
            });
        }

        public void setData(CardData data){
            title.setText(data.getTitle());
            description.setText(data.getDescription());
            image.setImageResource(data.getPicture());
            like.setChecked(data.isLike());
        }

    }
}
