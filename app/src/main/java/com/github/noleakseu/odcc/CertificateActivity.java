package com.github.noleakseu.odcc;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.noleakseu.odcc.databinding.ActivityReportBinding;
import com.google.iot.cbor.CborMap;
import com.google.iot.cbor.CborParseException;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import COSE.CoseException;
import COSE.EncryptMessage;
import COSE.Message;
import nl.minvws.encoding.Base45;

public class CertificateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityReportBinding viewBinding = ActivityReportBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        try {
            byte[] base45 = Base45.getDecoder().decode(new String(getIntent().getByteArrayExtra("BYTES"), StandardCharsets.UTF_8).substring(4));
            Inflater inflater = new Inflater();
            inflater.setInput(base45);
            byte[] decoded = new byte[base45.length * 2];
            int length = inflater.inflate(decoded);
            inflater.end();
            Message message = EncryptMessage.DecodeFromBytes(Arrays.copyOfRange(decoded, 0, length));
            if (message.HasContent()) {
                CborMap cborMap = CborMap.createFromCborByteArray(message.GetContent());
                viewBinding.names.setText(Util.getNames(cborMap));
                viewBinding.datesOfBirth.setText(Util.getDatesOfBirth(cborMap));
                viewBinding.report.setText(cborMap.toString(2));
            }
        } catch (DataFormatException | CoseException | CborParseException | IllegalArgumentException e) {
            Toast.makeText(
                    getApplicationContext(),
                    "ERROR: " + (null != e.getLocalizedMessage() ? e.getLocalizedMessage() : "unknown"),
                    Toast.LENGTH_LONG
            ).show();
            finish();
        }
    }
}