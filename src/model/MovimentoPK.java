/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Altair
 */
@Embeddable
public class MovimentoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idMovimento")
    private int idMovimento;
    @Basic(optional = false)
    @Column(name = "Usuario_idUsuario")
    private int usuarioidUsuario;
    @Basic(optional = false)
    @Column(name = "Produto_idProduto")
    private int produtoidProduto;
    @Basic(optional = false)
    @Column(name = "Pessoa_idPessoa")
    private int pessoaidPessoa;

    public MovimentoPK() {
    }

    public MovimentoPK(int idMovimento, int usuarioidUsuario, int produtoidProduto, int pessoaidPessoa) {
        this.idMovimento = idMovimento;
        this.usuarioidUsuario = usuarioidUsuario;
        this.produtoidProduto = produtoidProduto;
        this.pessoaidPessoa = pessoaidPessoa;
    }

    public int getIdMovimento() {
        return idMovimento;
    }

    public void setIdMovimento(int idMovimento) {
        this.idMovimento = idMovimento;
    }

    public int getUsuarioidUsuario() {
        return usuarioidUsuario;
    }

    public void setUsuarioidUsuario(int usuarioidUsuario) {
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public int getProdutoidProduto() {
        return produtoidProduto;
    }

    public void setProdutoidProduto(int produtoidProduto) {
        this.produtoidProduto = produtoidProduto;
    }

    public int getPessoaidPessoa() {
        return pessoaidPessoa;
    }

    public void setPessoaidPessoa(int pessoaidPessoa) {
        this.pessoaidPessoa = pessoaidPessoa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idMovimento;
        hash += (int) usuarioidUsuario;
        hash += (int) produtoidProduto;
        hash += (int) pessoaidPessoa;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovimentoPK)) {
            return false;
        }
        MovimentoPK other = (MovimentoPK) object;
        if (this.idMovimento != other.idMovimento) {
            return false;
        }
        if (this.usuarioidUsuario != other.usuarioidUsuario) {
            return false;
        }
        if (this.produtoidProduto != other.produtoidProduto) {
            return false;
        }
        if (this.pessoaidPessoa != other.pessoaidPessoa) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.MovimentoPK[ idMovimento=" + idMovimento + ", usuarioidUsuario=" + usuarioidUsuario + ", produtoidProduto=" + produtoidProduto + ", pessoaidPessoa=" + pessoaidPessoa + " ]";
    }
    
}
