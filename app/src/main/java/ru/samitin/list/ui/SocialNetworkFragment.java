package ru.samitin.list.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.samitin.list.R;
import ru.samitin.list.data.CardData;
import ru.samitin.list.data.CardsSource;
import ru.samitin.list.data.CardsSourceImpl;

public class SocialNetworkFragment extends Fragment {
    private CardsSource data;
    private SocialNetworkAdapter adapter;
    private RecyclerView recyclerView;
    public SocialNetworkFragment newInstance(){
        return new SocialNetworkFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_socialnetwork,container,false);
        RecyclerView recyclerView=view.findViewById(R.id.recycler_view_lines);
        data = new CardsSourceImpl(getResources()).init();
        initView(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.cards_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                data.addCardData(new CardData("Загаловок "+data.size(),
                        "Описание "+data.size(),R.drawable.nature1,false));
                adapter.notifyItemInserted(data.size()-1);
                recyclerView.scrollToPosition(data.size()-1);
                return true;
            case R.id.action_clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(View view){
        recyclerView=view.findViewById(R.id.recycler_view_lines);
        data=new CardsSourceImpl(getResources()).init();
        initReciclerView();
    }

    private void initReciclerView(){
        // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);
        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        // Установим адаптер
        adapter=new SocialNetworkAdapter(data);
        recyclerView.setAdapter(adapter);
        // Добавим разделитель карточек
        DividerItemDecoration itemDecoration=new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator,null));
        recyclerView.addItemDecoration(itemDecoration);

        adapter.setOnItemClickListener(new SocialNetworkAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), String.format("Позиция - %d", position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}