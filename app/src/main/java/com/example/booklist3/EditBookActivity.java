package com.example.booklist3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditBookActivity extends AppCompatActivity {


    private EditText editTextBookTitle;
    private Button btOk,btCancel;
    private int editPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        editTextBookTitle=(EditText)findViewById(R.id.editText_change);
        btCancel=(Button)findViewById(R.id.ButtonCancel);
        btOk=(Button)findViewById(R.id.buttonOk);

        editPosition=getIntent().getIntExtra("edit_position",0);
        editTextBookTitle.setText(getIntent().getStringExtra("book_title"));//把主界面的文本显示

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String title=editTextBookTitle.getText().toString();
                Intent intent=new Intent();
                intent.putExtra("edit_position", editPosition);
                intent.putExtra("book_title",editTextBookTitle.getText().toString());
                setResult(RESULT_OK,intent);
                EditBookActivity.this.finish();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditBookActivity.this.finish();
            }
        });
    }
}
