package com.bluebudy.SCQ.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.bluebudy.SCQ.domain.Analise;


@SuppressWarnings("serial")
public class AnaliseDTO implements Serializable {
	
	
	private Long id;
	
	private String analista;
	private Double resultado;
	private Date data;
	private Boolean status;
	private Long parametroId;
	private List<Long> ordensDeCorrecaoId;
        private Double pMax;
        private Double pMin;
        private Double pMaxT;
        private Double pMinT;
   private String nomeParametro;
	
	public AnaliseDTO(Analise analise) {
		this.id = analise.getId();
		this.analista = analise.getAnalista();
		this.resultado = analise.getResultado();
		this.data = analise.getData();
		this.status = analise.getNeedCorrecao();
		this.parametroId = analise.getParametro().getId();
        this.pMax = analise.getParametro().getpMax();
        this.pMin = analise.getParametro().getpMin();
        this.pMinT = analise.getParametro().getpMinT();
        this.pMaxT = analise.getParametro().getpMaxT();
        this.nomeParametro = analise.getParametro().getNome();
               
		this.ordensDeCorrecaoId = analise.getOrdemDeCorrecao() == null ? null : analise.getOrdemDeCorrecao().stream().map(e -> e.getId()).collect(Collectors.toList());		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAnalista() {
		return analista;
	}
	public void setAnalista(String analista) {
		this.analista = analista;
	}
	public Double getResultado() {
		return resultado;
	}
	public void setResultado(Double resultado) {
		this.resultado = resultado;
	}
	
	public Long getParametroId() {
		return parametroId;
	}

	public void setParametroId(Long parametroId) {
		this.parametroId = parametroId;
	}

	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}


	public List<Long> getOrdemDeCorrecaoId() {
		return ordensDeCorrecaoId;
	}

	public void setOrdemDeCorrecaoId(List<Long> ordemDeCorrecaoId) {
		this.ordensDeCorrecaoId = ordemDeCorrecaoId;
	}
	
	
        
        public String getNomeParametro() {
		return nomeParametro;
	}

	public void setNomeParametro(String nomeParametro) {
		this.nomeParametro = nomeParametro;
	}

		public double generateCorrecao(Analise analise){
            Double resultado = analise.getResultado();
            Double pMax = analise.getParametro().getpMax();
            Double pMin = analise.getParametro().getpMin();
            Double nominal = (pMax + pMin) / 2;
            if(resultado<nominal){
                return (nominal - resultado) * analise.getParametro().getEtapa().getVolume();
            } else {
                return 0;
            }
        }
        
        

	public static List<AnaliseDTO> analiseToAnaliseDTO(List<Analise> analises) {
		return analises.stream().map(AnaliseDTO::new).collect(Collectors.toList());
	}

    public List<Long> getOrdensDeCorrecaoId() {
        return ordensDeCorrecaoId;
    }

    public void setOrdensDeCorrecaoId(List<Long> ordensDeCorrecaoId) {
        this.ordensDeCorrecaoId = ordensDeCorrecaoId;
    }

    public Double getpMax() {
        return pMax;
    }

    public void setpMax(Double pMax) {
        this.pMax = pMax;
    }

    public Double getpMin() {
        return pMin;
    }

    public void setpMin(Double pMin) {
        this.pMin = pMin;
    }

    public Double getpMaxT() {
        return pMaxT;
    }

    public void setpMaxT(Double pMaxT) {
        this.pMaxT = pMaxT;
    }

    public Double getpMinT() {
        return pMinT;
    }

    public void setpMinT(Double pMinT) {
        this.pMinT = pMinT;
    }
    
    
	
        
	
	
	

}
