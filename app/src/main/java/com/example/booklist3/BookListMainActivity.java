package com.example.booklist3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookListMainActivity extends AppCompatActivity {

    //@Override
    public static final int CONTEXT_MENU_DELETE = 1;
    public static final int CONTEXT_MENU_NEW= CONTEXT_MENU_DELETE+1;
    public static final int CONTEXT_MENU_UPDATE =CONTEXT_MENU_NEW+1 ;
    public static final int CONTEXT_MENU_ABOUT = CONTEXT_MENU_UPDATE+1;
    public static final int REQUEST_CODE_NEW_BOOK=901;
    public static final int REQUEST_CODE_UPDATE_BOOK = 902;

    //@Override
    private ListView listViewBooks;
    private ArrayList<Book> listBooks = new ArrayList<>();
    private BookAdapter theAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);
        init();
        listViewBooks = (ListView) this.findViewById(R.id.list_view_books);
        theAdapter = new BookAdapter(BookListMainActivity.this, R.layout.list_view_item_book, listBooks);
        listViewBooks.setAdapter(theAdapter);
        this.registerForContextMenu(listViewBooks);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        if (v == listViewBooks) {
            // 获取适配器
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //设置标题
            menu.setHeaderTitle(listBooks.get(info.position).getTitle());
            //添加内容
            menu.add(0, CONTEXT_MENU_DELETE, 0, "删除");
            menu.add(0, CONTEXT_MENU_NEW, 0, "新建");
            menu.add(0, CONTEXT_MENU_UPDATE, 0, "修改");
            menu.add(0, CONTEXT_MENU_ABOUT, 0, "关于...");


        }
    }

    //onActivityResult（）是从EditBookActiviy回调到BookListActivity的
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_NEW_BOOK: {
                if (resultCode == RESULT_OK) {
                    int insertPosition = data.getIntExtra("edit_position", 0);
                    String title = data.getStringExtra("book_title");
                    listBooks.add(insertPosition, new Book(title, R.drawable.book_no_name));
                    theAdapter.notifyDataSetChanged();

                    Toast.makeText(this, "新建成功", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case REQUEST_CODE_UPDATE_BOOK: {
                if (resultCode == RESULT_OK) {
                    int position = data.getIntExtra("edit_position", 0);
                    String title = data.getStringExtra("book_title");
                    Book book = listBooks.get(position);
                    book.setTitle(title);
                    theAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case CONTEXT_MENU_DELETE: {

                final int removePosition = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
                new android.app.AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("询问")
                        .setMessage("你确定要删除这条吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listBooks.remove(removePosition);
                                theAdapter.notifyDataSetChanged();
                                Toast.makeText(BookListMainActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create().show();
                break;
            }
            case CONTEXT_MENU_NEW: {
                Intent intent1=new Intent(this,EditBookActivity.class);
                intent1.putExtra("edit_position",((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
                intent1.putExtra("book_title",0);
                startActivityForResult(intent1,REQUEST_CODE_NEW_BOOK);

                break;
            }
            case CONTEXT_MENU_UPDATE:{
                AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Book book=listBooks.get(menuInfo.position);
                Intent intent2=new Intent(this,EditBookActivity.class);
                 intent2.putExtra("edit_position",menuInfo.position);
                 intent2.putExtra("book_title",book.getTitle());
                startActivityForResult(intent2,REQUEST_CODE_UPDATE_BOOK);
                break;

            }
            case CONTEXT_MENU_ABOUT:
                Toast.makeText(this, "版权所有by Tanyirou!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }


    private void init() {
        listBooks=new ArrayList<Book>();
        listBooks.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        listBooks.add(new Book("创新工程实践", R.drawable.book_no_name));
        listBooks.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));

    }

    class BookAdapter extends ArrayAdapter<Book> {

        private int resourceId;

        public BookAdapter(Context context, int resource, List<Book> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            Book book = getItem(position);//获取当前项的实例
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            ((ImageView) view.findViewById(R.id.image_view_book_cover)).setImageResource(book.getCoverResourceId());
            ((TextView) view.findViewById(R.id.text_view_book_title)).setText(book.getTitle());
      return view;
        }
    }
}
