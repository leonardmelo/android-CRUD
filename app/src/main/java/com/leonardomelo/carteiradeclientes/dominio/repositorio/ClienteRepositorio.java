package com.leonardomelo.carteiradeclientes.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.leonardomelo.carteiradeclientes.dominio.entidades.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteRepositorio {

    private SQLiteDatabase conexao;

    public ClienteRepositorio(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

    public void inserir(Cliente cliente){

        ContentValues contentValues = new ContentValues();
        contentValues.put("NOME",cliente.nome);
        contentValues.put("ENDERECO",cliente.endereco);
        contentValues.put("TELEFONE",cliente.telefone);
        contentValues.put("EMAIL",cliente.email);

        conexao.insertOrThrow("CLIENTE",null,contentValues);
    }

    public void excluir(int id){

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);

        conexao.delete("CLIENTE","CODIGO = ?", parametros);

    }

    public void alterar(Cliente cliente){

        ContentValues contentValues = new ContentValues();
        contentValues.put("NOME",cliente.nome);
        contentValues.put("ENDERECO",cliente.endereco);
        contentValues.put("TELEFONE",cliente.telefone);
        contentValues.put("EMAIL",cliente.email);

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(cliente.codigo);

        conexao.update("CLIENTE", contentValues,"CODIGO = ?", parametros);

    }

    public List<Cliente> buscarTodos(){

        List<Cliente> clientes = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CODIGO, NOME, ENDERECO, EMAIL, TELEFONE");
        sql.append("    FROM CLIENTE");

        Cursor resultado = conexao.rawQuery(sql.toString(),null);

        if (resultado.getCount() > 0){

            resultado.moveToFirst();

            do {

                Cliente cli = new Cliente();

                cli.codigo = resultado.getInt( resultado.getColumnIndexOrThrow("CODIGO") );
                cli.nome = resultado.getString( resultado.getColumnIndexOrThrow("NOME") );
                cli.endereco = resultado.getString( resultado.getColumnIndexOrThrow("ENDERECO") );
                cli.telefone = resultado.getString( resultado.getColumnIndexOrThrow("TELEFONE") );
                cli.email = resultado.getString( resultado.getColumnIndexOrThrow("EMAIL") );

                clientes.add(cli);

            }while (resultado.moveToNext());

        }

        return clientes;
    }

    public Cliente buscarCliente(int id){

        Cliente cli = new Cliente();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT CODIGO, NOME, ENDERECO, EMAIL, TELEFONE");
        sql.append("    FROM CLIENTE");
        sql.append("    WHERE CODIGO = ?");

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);

        Cursor resultado = conexao.rawQuery(sql.toString(),parametros);

        if (resultado.getCount() > 0){

            resultado.moveToFirst();

            cli.codigo = resultado.getInt( resultado.getColumnIndexOrThrow("CODIGO") );
            cli.nome = resultado.getString( resultado.getColumnIndexOrThrow("NOME") );
            cli.endereco = resultado.getString( resultado.getColumnIndexOrThrow("ENDERECO") );
            cli.telefone = resultado.getString( resultado.getColumnIndexOrThrow("TELEFONE") );
            cli.email = resultado.getString( resultado.getColumnIndexOrThrow("EMAIL") );

            return cli;
        }

        return null;

    }

}