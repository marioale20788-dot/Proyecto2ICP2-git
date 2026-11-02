import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  imports: [],
  selector: 'app-modal-confirmation',

  templateUrl: './modal-confirmation.component.html',
})
export class ModalConfirmationComponent {


 @Input()
  idAEliminar!: string;

  @Output()
  ConfirmacionEliminar = new EventEmitter<void>();

  executeConfirmation(): void {
    this.ConfirmacionEliminar.emit();
  }


}
