package med.voll.api.domain.consultas;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.medicos.Medico;
import med.voll.api.domain.pacientes.Paciente;

import java.time.LocalDateTime;

@Table(name = "consultas")
@Entity(name = "Consulta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    @Column(name = "motivo_cancelamento")
    private MotivoCancelamento motivoCancelamento;

    public Consulta(Medico medico, Paciente paciente, LocalDateTime data){
        this.medico = medico;
        this.paciente = paciente;
        this.data = data;
    }

    public void cancelar(MotivoCancelamento motivo){
        this.motivoCancelamento = motivo;
    }
}
