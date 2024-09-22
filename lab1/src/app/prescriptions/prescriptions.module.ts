import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrescriptionsCenterComponent } from './prescriptions-center/prescriptions-center.component';
import { PrescriptionsListComponent } from './prescriptions-list/prescriptions-list.component';
import { PrescriptionsDetailsComponent } from './prescriptions-details/prescriptions-details.component';
import { PrescriptionsRoutingModule } from './prescriptions-routing.module';
import { PrescriptionFormComponent } from './prescription-form/prescription-form.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    PrescriptionsCenterComponent,
    PrescriptionsListComponent,
    PrescriptionsDetailsComponent,
    PrescriptionFormComponent,
  ],
  imports: [CommonModule, PrescriptionsRoutingModule, FormsModule],
})
export class PrescriptionsModule {}
