package com.btten.hcb.book;

import com.btten.hcbvip.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BookListAdapter extends BaseAdapter {
	Activity context = null;
	BookListItem[] items;

	public BookListAdapter(Activity context, BookListItem[] items) {
		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vHolder;
		LayoutInflater inflater = null;

		if (convertView == null) {
			vHolder = new ViewHolder();
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.book_list_item, null);
			vHolder.txtTitle = (TextView) convertView
					.findViewById(R.id.book_list_item_title);
			vHolder.txtAuthor = (TextView) convertView
					.findViewById(R.id.book_list_item_author);
			vHolder.txtSynopsis = (TextView) convertView
					.findViewById(R.id.book_list_item_synopsis);
			vHolder.imageView = (ImageView) convertView
					.findViewById(R.id.book_list_item_image);
			vHolder.btnSubmit = (Button) convertView
					.findViewById(R.id.book_list_item_button);
			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}

		vHolder.txtTitle.setText(items[position].title);
		vHolder.txtAuthor.setText(items[position].author);
		vHolder.txtSynopsis.setText(items[position].synopsis);

		vHolder.btnSubmit.setOnClickListener(new adapterListner(position));
		ImageLoader.getInstance().displayImage(items[position].image,
				vHolder.imageView);

		return convertView;
	}

	class adapterListner implements OnClickListener {
		int index;

		public adapterListner(int index) {
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(context, BookInfoActivity.class);
			intent.putExtra("KEY_ID", items[index].id);
			context.startActivity(intent);
		}
	}

	@Override
	public int getCount() {
		return items.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public class ViewHolder {
		TextView txtTitle, txtAuthor, txtSynopsis;
		ImageView imageView;
		Button btnSubmit;
	}
}
