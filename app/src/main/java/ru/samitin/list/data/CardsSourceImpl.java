package ru.samitin.list.data;

import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

import ru.samitin.list.R;

public class CardsSourceImpl implements CardsSource{
    private List<CardData>dataSource;
    private Resources resources;
    public CardsSourceImpl(Resources resources){
        this.resources=resources;
        dataSource=new ArrayList<>(7);
    }
    public CardsSourceImpl init(){
        String[]titles=resources.getStringArray(R.array.titles);
        String[]descriptions=resources.getStringArray(R.array.descriptions);
        int[]pictures=getImageArray();
        for (int i=0;i<titles.length;i++)
            dataSource.add(new CardData(titles[i],descriptions[i],pictures[i],false));
        return this;
    }
    private int[] getImageArray(){
        TypedArray typedArray=resources.obtainTypedArray(R.array.pictures);
        int[] arrayId=new int[typedArray.length()];
        for (int i=0;i<typedArray.length();i++){
            arrayId[i]=typedArray.getResourceId(i,0);
        }
        return arrayId;
    }
    @Override
    public CardData getCardData(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateCardData(int position, CardData cardData) {
        dataSource.set(position,cardData);
    }

    @Override
    public void addCardData(CardData cardData) {
        dataSource.add(cardData);
    }

    @Override
    public void clearCardData() {
        dataSource.clear();
    }
}
