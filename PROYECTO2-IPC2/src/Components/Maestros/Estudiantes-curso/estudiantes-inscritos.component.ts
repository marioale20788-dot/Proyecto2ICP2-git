import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { servicios } from '../../../Services/Eventos/event.service';
import { EstudianteAsistenciaRow, NuevaAsistenciaRequest } from '../../../Models/Maestro/Asistencia';

@Component({
  selector: 'app-estudiantes-inscritos',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './estudiantes-inscritos.component.html'
})
export class EstudiantesInscritosComponent implements OnInit {

  public idSeccion = signal<string>('');
  public ciclo = signal<number>(0);
  public idAsignacion = signal<string>('');
  public nombreCurso = signal<string>('');
  public nombreSeccion = signal<string>('');

  public modoAsistencia = signal<boolean>(false);
  public fechaSeleccionada = signal<string>(new Date().toISOString().substring(0, 10));
  public estudiantes = signal<EstudianteAsistenciaRow[]>([]);

  public errorMsg = signal<string | null>(null);
  public exitoMsg = signal<string | null>(null);

  constructor(
    private route: ActivatedRoute,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.idSeccion.set(params['idSeccion'] || '');
      this.ciclo.set(Number(params['ciclo']) || 0);
      this.idAsignacion.set(params['idAsignacion'] || '');
      this.nombreCurso.set(params['nombreCurso'] || '');
      this.nombreSeccion.set(params['nombreSeccion'] || '');

      if (this.idSeccion() && this.ciclo() && this.idAsignacion()) {
        this.cargarEstudiantes();
      }
    });
  }

  cargarEstudiantes(): void {
    this.errorMsg.set(null);
    this.exitoMsg.set(null);

    const params = {
      idSeccion: this.idSeccion(),
      ciclo: this.ciclo(),
      idAsignacion: this.idAsignacion()
    };


    this.servicios.obtenerEstudiantesInscritos(params).subscribe({
      next: (data) => {
        const lista: EstudianteAsistenciaRow[] = [];
        
        for (let est of data) {
          lista.push({
            idEstudiante: est.idEstudiante,
            nombreEstudiante: est.nombreEstudiante,
            estado: true,
            faltas: 0,
            llegadas: 0
          });
        }

        this.estudiantes.set(lista);

        this.cargarAsistencias();
      },
      error: () => {
        this.estudiantes.set([]);
        this.errorMsg.set('No se encontraron estudiantes para la sección seleccionada.');
      }
    });
  }

  cargarAsistencias(): void {
    this.servicios.obtenerEstadoAsistencia(this.idAsignacion()).subscribe({
      next: (asistencias) => {
        const listaActual = [...this.estudiantes()];


        for (let est of listaActual) {
          for (let asig of asistencias) {
            if (est.idEstudiante === asig.idEstudiante) {
              est.faltas = asig.faltas;
              est.llegadas = asig.llegadas;
            }
          }
        }

        this.estudiantes.set(listaActual);
      },
      error: () => {
      
      }
    });
  }

  onFechaChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.fechaSeleccionada.set(input.value);
  }

  toggleEstadoEstudiante(index: number, event: Event): void {
    const checkbox = event.target as HTMLInputElement;
    const listaActualizada = [...this.estudiantes()];
    listaActualizada[index].estado = checkbox.checked;
    this.estudiantes.set(listaActualizada);
  }

  marcarTodos(estado: boolean): void {
    const actualizada = this.estudiantes().map(est => ({ ...est, estado }));
    this.estudiantes.set(actualizada);
  }

  guardarAsistencia(): void {
    if (!this.fechaSeleccionada()) {
      this.errorMsg.set('Debes seleccionar una fecha de asistencia válida.');
      return;
    }

    this.errorMsg.set(null);
    this.exitoMsg.set(null);

    const asistencia: NuevaAsistenciaRequest[] = this.estudiantes().map(est => ({
      fecchaAsistencia: this.fechaSeleccionada(),
      estado: est.estado,
      idEstudiante: est.idEstudiante,
      idAsignacion: this.idAsignacion()
    }));

    this.servicios.registrarAsistencia(asistencia).subscribe({
      next: () => {
        this.exitoMsg.set('¡Asistencia registrada exitosamente!');
        this.modoAsistencia.set(false); 
        this.cargarEstudiantes();
      },
      error: (err) => {
        if (err.status === 400) {
          this.errorMsg.set('Los datos enviados no son válidos (HTTP 400).');
        } else {
          this.errorMsg.set('Ocurrió un error al guardar la asistencia.');
        }
      }
    });
  }
}