import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { servicios } from '../../../Services/Eventos/event.service';
import { ActividadCabecera, EstudianteMatriz, GetNotas, NuevanotaFinalRequest, NuevaNotaRequest } from '../../../Models/Notas/Notas';



@Component({
  selector: 'app-notas',

  imports: [RouterLink],
  templateUrl: './notas.component.html'
})
export class NotasComponent implements OnInit {

   idAsignacion = signal<string>('');
   idSeccion = signal<string>('');
   ciclo = signal<number>(0);
   codigoCurso = signal<string>('');
   nombreCurso = signal<string>('');
   nombreSeccion = signal<string>('');


   actividadesCabecera = signal<ActividadCabecera[]>([]);

   estudiantes = signal<EstudianteMatriz[]>([]);

   errorMsg = signal<string | null>(null);
   exitoMsg = signal<string | null>(null);

  constructor(
    private route: ActivatedRoute,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.idAsignacion.set(params['idAsignacion'] || '');
      this.idSeccion.set(params['idSeccion'] || '');
      this.ciclo.set(Number(params['ciclo']) || 0);
      this.codigoCurso.set(params['codigoCurso'] || '');
      this.nombreCurso.set(params['nombreCurso'] || '');
      this.nombreSeccion.set(params['nombreSeccion'] || '');

      if (this.idAsignacion()) {
        this.cargarMatrizNotas();
      }
    });
  }

  cargarMatrizNotas(): void {
    this.errorMsg.set(null);
    this.exitoMsg.set(null);

    const queryParams = {
      idSeccion: this.idSeccion(),
      ciclo: this.ciclo(),
      idAsignacion: this.idAsignacion()
    };

  
    this.servicios.obtenerEstudiantesInscritos(queryParams).subscribe({
      next: (alumnos) => {
        const listaFilas: EstudianteMatriz[] = [];

        for (let est of alumnos) {
          listaFilas.push({
            idEstudiante: est.idEstudiante,
            nombreEstudiante: est.nombreEstudiante,
            notas: [],
            notaFinalInput: 0
          });
        }

        this.estudiantes.set(listaFilas);
        this.cargarNotasEstudiantes(listaFilas);
      },
      error: (err: HttpErrorResponse) => {
        this.estudiantes.set([]);
        this.errorMsg.set(err.error);
      }
    });
  }

  cargarNotasEstudiantes(filasActuales: EstudianteMatriz[]): void {
    const idRegistro = this.idAsignacion();
    const listaCabecera: ActividadCabecera[] = [];

    for (let est of filasActuales) {
      this.servicios.obtenerNotasEstudiante(idRegistro, est.idEstudiante).subscribe({
        next: (notasObtenidas) => {
          est.notas = notasObtenidas;

          for (let nota of notasObtenidas) {
            let existe = false;
            for (let item of listaCabecera) {
              if (item.idActividad === nota.idActividad) {
                existe = true;
                break;
              }
            }

            if (!existe) {
              listaCabecera.push({
                idActividad: nota.idActividad,
                nombreActividad: nota.nombreActividad,
                punteoDefinido: nota.punteoDefinido
              });
            }
          }

          this.actividadesCabecera.set([...listaCabecera]);
          this.estudiantes.set([...filasActuales]);
        },
        error: (err: HttpErrorResponse) => {
          this.errorMsg.set(err.error);
        }
      });
    }
  }

  obtenerPunteo(est: EstudianteMatriz, idActividad: string): number {
    for (let n of est.notas) {
      if (n.idActividad === idActividad) {
        return n.punteActividad || 0;
      }
    }
    return 0;
  }

 
  notaActividadInput(est: EstudianteMatriz, idActividad: string, event: Event): void {
    const input = event.target as HTMLInputElement;
    const valor = Number(input.value);

    for (let n of est.notas) {
      if (n.idActividad === idActividad) {
        n.punteActividad = valor;
        break;
      }
    }
  }


  notaFinalInput(est: EstudianteMatriz, event: Event): void {
    const input = event.target as HTMLInputElement;
    est.notaFinalInput = Number(input.value);
  }

  guardarNotaActividad(est: EstudianteMatriz, idActividad: string): void {
    let notaEncontrada: GetNotas | null = null;
    for (let n of est.notas) {
      if (n.idActividad === idActividad) {
        notaEncontrada = n;
        break;
      }
    }

    if (!notaEncontrada) return;

    this.errorMsg.set(null);
    this.exitoMsg.set(null);

    const nota: NuevaNotaRequest = {
      idEstudiante: est.idEstudiante,
      idActividad: idActividad,
      punteo: notaEncontrada.punteActividad
    };

    this.servicios.insertarNotaActividad(nota).subscribe({
      next: () => {
        this.exitoMsg.set(`Nota actualizada para ${est.nombreEstudiante}.`);
      },
      error: (err: HttpErrorResponse) => {
        this.errorMsg.set(err.error);
      }
    });
  }

  guardarNotaFinal(est: EstudianteMatriz): void {
    this.errorMsg.set(null);
    this.exitoMsg.set(null);

    const notaFinal: NuevanotaFinalRequest = {
      notaFinal: est.notaFinalInput,
      idEstudiante: est.idEstudiante,
      codigoCurso: this.codigoCurso(),
      cicloLectivo: this.ciclo()
    };

    this.servicios.insertarNotaFinal(notaFinal).subscribe({
      next: () => {
        this.exitoMsg.set(`Nota final guardada exitosamente para ${est.nombreEstudiante}.`);
      },
      error: (err: HttpErrorResponse) => {
        this.errorMsg.set(err.error);
      }
    });
  }
}