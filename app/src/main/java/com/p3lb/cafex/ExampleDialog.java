package com.p3lb.cafex;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.p3lb.cafex.MenuRefund.TampilRefund;
import com.p3lb.cafex.MenuTransaksi.TampilDataMenu;

public class ExampleDialog extends AppCompatDialogFragment {
    EditText edt_idtransaksi;
    private ExampleDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("Kode Transaksi")
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String edtidtransaksi = edt_idtransaksi.getText().toString();
                        listener.applyTexts(edtidtransaksi);
                    }
                });

        edt_idtransaksi = view.findViewById(R.id.edt_idtransaksi);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Harus implementasi dialoglistener");
        }
    }

    public interface ExampleDialogListener{
        void applyTexts(String edt_idtransaksi);
    }
}
