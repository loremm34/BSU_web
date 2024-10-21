import { Component } from '@angular/core';
import { PrescriptionService } from '../services/prescriptions.service';
import { Prescriptions } from '../services/mock-prescriptions-list';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { Prescriptionss } from '../services/mock-prescriptions-list';

@Component({
  selector: 'app-prescription-form',
  templateUrl: './prescription-form.component.html',
  styleUrls: ['./prescription-form.component.sass'],
})
export class PrescriptionFormComponent {
  prescription: Prescriptions = {
    id: '0',
    description: '',
    doctor: '',
    patient: '',
  };

  constructor(
    private prescriptionService: PrescriptionService,
    private router: Router
  ) {}

  async onSubmit(prescriptionForm: any): Promise<void> {
    const prescriptions = await firstValueFrom(
      this.prescriptionService.getPrescriptionss()
    );
    const existingPrescription = prescriptions?.find(
      (m) => m.id === this.prescription.id
    );

    if (existingPrescription) {
      await this.onUpdate();
    } else {
      await this.onAdd();
    }

    prescriptionForm.reset();
    this.router.navigate(['']);
  }

  async onAdd(): Promise<void> {
    const prescriptions = await firstValueFrom(
      this.prescriptionService.getPrescriptionss()
    );

    this.prescription.id =
      prescriptions && prescriptions.length > 0
        ? (Math.max(...prescriptions.map((m) => Number(m.id))) + 1).toString()
        : '1';

    await this.prescriptionService.addPrescriptions(this.prescription);
    console.log('Prescriptions Added:', prescriptions);
  }

  async onUpdate(): Promise<void> {
    if (!this.prescription.id) {
      console.error('Prescription ID is required for updating.');
      return;
    }

    await this.prescriptionService.updatePrescriptions(this.prescription);
    console.log('Prescription Updated:', this.prescription);
  }

  async onDelete(): Promise<void> {
    await this.prescriptionService.deletePrescriptions(
      this.prescription.id.toString()
    );
    console.log('Prescription Deleted:', this.prescription);
    this.router.navigate(['']);
  }

  goBack(): void {
    this.router.navigate(['']);
  }

  async loadMockPrescriptions(): Promise<void> {
    await this.prescriptionService.loadMockPrescriptions(Prescriptionss);
    console.log('Prescriptions Loaded from Mock List');
    this.router.navigate(['']);
  }
}
