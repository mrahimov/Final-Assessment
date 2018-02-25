package conteiner;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by murodjon.rahimov on 2/25/18.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.ImageView;
import com.example.murodjonrahimov.finalassessment.PhotoActivity;
import com.example.murodjonrahimov.finalassessment.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.ViewHolder>{

  public static final String URL_KEY = "url";
  List<String> images = new ArrayList<>();

  @Override
  public DogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    return new ViewHolder(inflater.inflate(R.layout.dog_item_view, parent, false));
  }

  @Override
  public void onBindViewHolder(final DogAdapter.ViewHolder holder, int position) {
    final String url = images.get(position);
    Picasso.with(holder.imageView.getContext()).load(url).into(holder.imageView);
    holder.imageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Context context = holder.imageView.getContext();
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra(URL_KEY, url);
        context.startActivity(intent);
      }
    });
  }

  @Override
  public int getItemCount() {
    return images.size();
  }

  public void setData(List<String> images) {
    this.images = images;
  }


  static class ViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;

    ViewHolder(View itemView) {
      super(itemView);
      imageView = itemView.findViewById(R.id.image);
    }
  }
}