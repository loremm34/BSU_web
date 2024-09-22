import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { PrescriptionsCenterComponent } from './prescriptions/prescriptions-center/prescriptions-center.component';
import { PrescriptionsDetailsComponent } from './prescriptions/prescriptions-details/prescriptions-details.component';
import { PrescriptionFormComponent } from './prescriptions/prescription-form/prescription-form.component';
import { PrescriptionsListComponent } from './prescriptions/prescriptions-list/prescriptions-list.component';
const routes: Routes = [
  { path: '', component: PrescriptionsCenterComponent },
  { path: 'details/:id', component: PrescriptionsDetailsComponent },
  { path: 'list', component: PrescriptionsListComponent },
  { path: 'prescription-form', component: PrescriptionFormComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
