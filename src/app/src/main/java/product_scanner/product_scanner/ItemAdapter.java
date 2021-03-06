package product_scanner.product_scanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<ItemProduct>{
    private Context context;
    private ArrayList<ItemProduct> list;
    private int resource;
    public ItemAdapter(Context context,int resource, ArrayList<ItemProduct> itemList) {
        super(context, resource, itemList);
        this.context = context;
        list=itemList;
        this.resource=resource;
    }
    public class ViewHolder{
    TextView name, price,quantity;
    ImageButton remove;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public ItemProduct getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder=new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(this.resource, null);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.price =  convertView.findViewById(R.id.price);
            viewHolder.quantity = convertView.findViewById(R.id.quantity);
            viewHolder.remove = convertView.findViewById(R.id.remove_but);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder= (ViewHolder) convertView.getTag();

        setText(viewHolder,position);
        viewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle(context.getString(R.string.do_you_want_to_delete))
                       .setNegativeButton(R.string.no,null)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ResideMenu.addToCartDatabase.deleteData(position);
                                list.remove(position);
                                notifyDataSetChanged();
                                Resume.resumeActivity(context);

                            }
                        });
                builder.show();

            }

        });
        return convertView;
    }

    private void setText(ViewHolder v,int postition) {
        v.name.setText(list.get(postition).getName());
        v.price.setText(list.get(postition).getPrice());
        v.quantity.setText(list.get(postition).getQuantity());
    }
}
