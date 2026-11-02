import { Routes } from '@angular/router';

import { UpdateLibroPageComponent } from '../Pages/Libros/Libros-page-update/update-libro-page.component/update-libro-page.component';
import { LibrosPage } from '../Pages/Libros/Libros-page/libros-page';
import { CreateLibroPageComponent } from '../Pages/Libros/Libros-page-create/create-libro-page.component/create-libro-page.component';
import { UsuariosPageComponent } from '../Pages/Usuarios/Usuarios-page/usuarios-page.component';
import { UsuarioPageUpdateComponent } from '../Pages/Usuarios/Usuarios-page-Update/usuario-page-update.component';
import { UsuariosPageCreateComponent } from '../Pages/Usuarios/Usuarios-page-create/usuarios-page-create.component';
import { EstudiantesPage } from '../Pages/Estudiantes/estudiantes-page.component';
import { UpdateEstudiantePageComponent } from '../Pages/Estudiantes/Update-estudiante-page/update-estudiante-page.component/update-estudiante-page.component';
import { CreateEstudiantePageComponent } from '../Pages/Estudiantes/Estuiantes -page-create/create-estudiante-page.component';
import { CiclosPage } from '../Pages/CicloLectivo/cicloLectivo-page/ciclos-page.component';
import { CreateCicloPageComponent } from '../Pages/CicloLectivo/cicloLectivo-page-create/create-ciclo-page.component';
import { UpdateCicloPageComponent } from '../Pages/CicloLectivo/cicloLectivo-page-update/update-ciclo-page.component';
import { GradosPage } from '../Pages/Grado/Grado-page/grados-page.component';
import { CreateGradoPageComponent } from '../Pages/Grado/Grado-create/create-grado.component';
import { CursosPage } from '../Pages/Curso/Curso-page/curso-page';
import { CreateCursoPageComponent } from '../Pages/Curso/Curso-create/create-curso-page.component';
import { EditCursoPageComponent } from '../Pages/Curso/curso-up/editar-curso-page.component';
import { SeccionesPage } from '../Pages/Secciones/Seccion page/secciones-page';
import { CreateSeccionPageComponent } from '../Pages/Secciones/seccion create/create-seccion-page.component';
import { EditSeccionPageComponent } from '../Pages/Secciones/seccion update/editar-seccion-page.component';
import { CarrerasPage } from '../Pages/Carrera/Carrera-page/carrera-page';
import { CreateCarreraPageComponent } from '../Pages/Carrera/Carrera-Create/create-carrera-page.component';
import { EditCarreraPageComponent } from '../Pages/Carrera/Carrera-update/editar-carrera-page.component';
import { CurriculoPage } from '../Pages/Curriculo/Curriculo - page/curriculo-page';
import { CreateCurriculoPageComponent } from '../Pages/Curriculo/Curriculo -create/create-curriculo-page.component';
import { AsignacionDocentePage } from '../Pages/AsignacionDocente/Asignacion-page/asignacion-docente-page';
import { CreateAsignacionDocenteComponent } from '../Pages/AsignacionDocente/Asignacion-Create/create-asignacion-docete.component';
import { InscripcionesPage } from '../Pages/Inscripcioin/page inscripcion/inscripciones-pate';
import { CreateInscripcionPageComponent } from '../Pages/Inscripcioin/create inscripcion/create-inscripcion-page.component';
import { UpdateInscripcionPageComponent } from '../Pages/Inscripcioin/update inscripcion/update-inscripcion-page.component';
import { Component } from '@angular/core';
import { PagoPageComponent } from '../Pages/Pagos/Page - pagos/pago-page.component';
import { SolvenciaComponent } from '../Pages/ExpedienteEstudiante/SolvenciaDePago/solvencia-pago.component';
import { HomeCmponent } from '../Pages/Inicio/home.cmponent';

import { LoginComponent } from '../Components/Login/Login/login.component';
import { MaestroAsignacionesComponent } from '../Components/Maestros/Maestro componente/maestro-asignaciones.component';
import { EstudiantesInscritosComponent } from '../Components/Maestros/Estudiantes-curso/estudiantes-inscritos.component';
import { ZonaActividadesComponent } from '../Components/Maestros/Zona-curso/zona-actividades.componente';
import { NotasComponent } from '../Components/Maestros/Notas-estudiante /notas.component';
import { PrestamosPage } from '../Pages/Prestamo/Prestamo-page/prestamo-page';
import { UpdatePrestamoPageComponent } from '../Pages/Prestamo/Presta-up/update-prestamo-page.component';
import { CreatePrestamoPageComponent } from '../Pages/Prestamo/Prestamo-create/create-prestamo-page.component';
import { DevolverPrestamoPageComponent } from '../Pages/Prestamo/prestamo devolver/devolver-prestamo-page.component';
import { PerfilInfoComponent } from '../Components/Perfil estudiante/Perfil/perfil-info.component';
import { PerfilNotasComponent } from '../Components/Perfil estudiante/Notas/perfil-notas.component';
import { PerfilPagosComponent } from '../Components/Perfil estudiante/Pagos/perfil-pagos.component';
import { PerfilBibliotecaComponent } from '../Components/Perfil estudiante/Biblioteca/perfil-biblioteca.component';
import { ReporteMorososComponent } from '../Pages/Reportes/reporte-morosos.component';
import { ReporteAcademicoComponent } from '../Pages/Reportes/Reporte academico/reporte-academico.component';
import { EstadoCuentaComponent } from '../Pages/Reportes/Estado cuenta/estado-cuenta.component';
import { ReporteIngresosComponent } from '../Pages/Reportes/Reporte ingresos/reporte-ingresos.component';
import { ReporteBibliotecaComponent } from '../Pages/Reportes/Reporte biblioteca/reporte-biblioteca.component';
import { AuthGuard } from '../Components/Login/Guard/AuthGuard';
import { roleGuard } from '../Components/Login/Guard/RoleaGuard';


export const routes: Routes = [
  { path: 'login', component: LoginComponent },

  {
    path: '',
    canActivate: [AuthGuard],
    children: [
      { 
        path: 'Home', 
        component: HomeCmponent 
      },
      {
        path: 'Usuarios',
        canActivate: [roleGuard],
        data: { roles: ['Super_Admin', 'Admin'] },
        children: [
          { path: '', component: UsuariosPageComponent },
          { path: 'Update/:id', component: UsuarioPageUpdateComponent },
          { path: 'ingresarEmpleado', component: UsuariosPageCreateComponent }
        ]
      },
      {
        path: 'ReporteBiblioteca',
        canActivate: [roleGuard],
        data: { roles: ['Super_Admin', 'Admin', 'Bibliotecario'] },
        children: [
          { path: 'ReporteBiblioteca', component: ReporteBibliotecaComponent }
        ]
      },
      {
        path: 'Libros',
        canActivate: [roleGuard],
        data: { roles: ['Bibliotecario'] },
        children: [
          { path: '', component: LibrosPage },
          { path: 'Update/:id', component: UpdateLibroPageComponent },
          { path: 'crearLibro', component: CreateLibroPageComponent }
        ]
      },
      {
        path: 'Prestamos',
        canActivate: [roleGuard],
        data: { roles: ['Bibliotecario'] },
        children: [
          { path: '', component: PrestamosPage },
          { path: 'Update/:id', component: UpdatePrestamoPageComponent },
          { path: 'Devolver/:id', component: DevolverPrestamoPageComponent },
          { path: 'crearPrestamo', component: CreatePrestamoPageComponent }
        ]
      },
      {
        path: 'Estudiantes',
        canActivate: [roleGuard],
        data: { roles: ['Super_Admin', 'Admin'] },
        children: [
          { path: '', component: EstudiantesPage },
          { path: 'Update/:dpi', component: UpdateEstudiantePageComponent },
          { path: 'crearEstudiante', component: CreateEstudiantePageComponent },
          { path: 'ReporteAcademico', component: ReporteAcademicoComponent },
          { path: 'ReporteEstadoCuenta', component: EstadoCuentaComponent }
        ]
      },
      {
        path: 'CicloLectivo',
        canActivate: [roleGuard],
        data: { roles: ['Super_Admin'] },
        children: [
          { path: '', component: CiclosPage },
          { path: 'Crear', component: CreateCicloPageComponent },
          { path: 'Update/:id', component: UpdateCicloPageComponent },
          { path: 'Grado', component: GradosPage },
          { path: 'Grado/Crear', component: CreateGradoPageComponent },
          { path: 'ReporteIngresos', component: ReporteIngresosComponent }
        ]
      },
      {
        path: 'SuperAdmin',
        canActivate: [roleGuard],
        data: { roles: ['Admin'] },
        children: [
          { path: 'Curso', component: CursosPage },
          { path: 'Curso/Crear', component: CreateCursoPageComponent },
          { path: 'Curso/Editar/:id', component: EditCursoPageComponent },
          { path: 'Grado', component: GradosPage },
          { path: 'Seccion', component: SeccionesPage },
          { path: 'Seccion/Crear', component: CreateSeccionPageComponent },
          { path: 'Seccion/Editar/:id', component: EditSeccionPageComponent },
          { path: 'Carrera', component: CarrerasPage },
          { path: 'Carrera/Crear', component: CreateCarreraPageComponent },
          { path: 'Carrera/Editar/:id', component: EditCarreraPageComponent },
          { path: 'Curriculo', component: CurriculoPage },
          { path: 'Curriculo/Crear', component: CreateCurriculoPageComponent },
          { path: 'AsignacionDocente', component: AsignacionDocentePage },
          { path: 'AsignacionDocente/Crear', component: CreateAsignacionDocenteComponent }
        ]
      },
      {
        path: 'MAESTRO',
        canActivate: [roleGuard],
        data: { roles: ['Maestro'] },
        children: [
          { path: '', redirectTo: 'Maestro', pathMatch: 'full' },
          { path: 'Maestro', component: MaestroAsignacionesComponent },
          { path: 'estudiantes', component: EstudiantesInscritosComponent },
          { path: 'zona', component: ZonaActividadesComponent },
          { path: 'notas', component: NotasComponent }
        ]
      },
      {
        path: 'ESTUDIANTE',
        canActivate: [roleGuard],
        data: { roles: ['Estudiante'] },
        children: [
          { path: '', redirectTo: 'Perfil', pathMatch: 'full' },
          { path: 'Perfil', component: PerfilInfoComponent },
          { path: 'NotasEstudiante', component: PerfilNotasComponent },
          { path: 'Pagos', component: PerfilPagosComponent },
          { path: 'Biblioteca', component: PerfilBibliotecaComponent }
        ]
      },
      {
        path: '',
        canActivate: [roleGuard],
        data: { roles: ['Secretaria'] },
        children: [
          { path: 'Inscripciones', component: InscripcionesPage },
          { path: 'Inscripciones/Crear', component: CreateInscripcionPageComponent },
          { path: 'Inscripciones/Update/:id', component: UpdateInscripcionPageComponent },
          { path: 'pagos', component: PagoPageComponent },
          { path: 'Solvencia', component: SolvenciaComponent }
        ]
      },
      {
        path: '',
        canActivate: [roleGuard],
        data: { roles: ['Secretaria', 'Admin', 'Super_Admin'] },
        children: [
          { path: 'Reporte', component: ReporteMorososComponent }
        ]
      }
    ]
  },

  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' }
];