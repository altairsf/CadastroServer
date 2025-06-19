/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Altair
 */
@Entity
@Table(name = "Movimento")
@NamedQueries({
    @NamedQuery(name = "Movimento.findAll", query = "SELECT m FROM Movimento m"),
    @NamedQuery(name = "Movimento.findByIdMovimento", query = "SELECT m FROM Movimento m WHERE m.movimentoPK.idMovimento = :idMovimento"),
    @NamedQuery(name = "Movimento.findByUsuarioidUsuario", query = "SELECT m FROM Movimento m WHERE m.movimentoPK.usuarioidUsuario = :usuarioidUsuario"),
    @NamedQuery(name = "Movimento.findByProdutoidProduto", query = "SELECT m FROM Movimento m WHERE m.movimentoPK.produtoidProduto = :produtoidProduto"),
    @NamedQuery(name = "Movimento.findByPessoaidPessoa", query = "SELECT m FROM Movimento m WHERE m.movimentoPK.pessoaidPessoa = :pessoaidPessoa"),
    @NamedQuery(name = "Movimento.findByQuantidade", query = "SELECT m FROM Movimento m WHERE m.quantidade = :quantidade"),
    @NamedQuery(name = "Movimento.findByTipo", query = "SELECT m FROM Movimento m WHERE m.tipo = :tipo"),
    @NamedQuery(name = "Movimento.findByValorUnitario", query = "SELECT m FROM Movimento m WHERE m.valorUnitario = :valorUnitario")})
public class Movimento implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MovimentoPK movimentoPK;
    @Column(name = "quantidade")
    private Integer quantidade;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "valorUnitario")
    private Integer valorUnitario;
    @JoinColumn(name = "Pessoa_idPessoa", referencedColumnName = "idPessoa", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pessoa pessoa;
    @JoinColumn(name = "Produto_idProduto", referencedColumnName = "idProduto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Produto produto;
    @JoinColumn(name = "Usuario_idUsuario", referencedColumnName = "idUsuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Movimento() {
    }
   
    public Movimento(MovimentoPK movimentoPK) {
        this.movimentoPK = movimentoPK;
    }

    public Movimento(int idMovimento, int usuarioidUsuario, int produtoidProduto, int pessoaidPessoa) {
        this.movimentoPK = new MovimentoPK(idMovimento, usuarioidUsuario, produtoidProduto, pessoaidPessoa);
    }

    public MovimentoPK getMovimentoPK() {
        return movimentoPK;
    }

    public void setMovimentoPK(MovimentoPK movimentoPK) {
        this.movimentoPK = movimentoPK;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Integer valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (movimentoPK != null ? movimentoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimento)) {
            return false;
        }
        Movimento other = (Movimento) object;
        if ((this.movimentoPK == null && other.movimentoPK != null) || (this.movimentoPK != null && !this.movimentoPK.equals(other.movimentoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Movimento[ movimentoPK=" + movimentoPK + " ]";
    }
    
}
