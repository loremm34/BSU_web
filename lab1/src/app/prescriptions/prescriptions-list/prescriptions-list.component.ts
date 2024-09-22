import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PrescriptionService } from '../services/prescriptions.service';
import { Prescriptions } from '../services/mock-prescriptions-list';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-prescriptions-list',
  templateUrl: './prescriptions-list.component.html',
  styleUrls: ['./prescriptions-list.component.sass'],
})
export class PrescriptionsListComponent implements OnInit {
  prescriptionss$: Observable<Prescriptions[]> = new Observable();

  constructor(
    private prescriptionsService: PrescriptionService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.prescriptionss$ = this.prescriptionsService.getPrescriptionss();
  }

  onSelect(prescription: Prescriptions): void {
    this.router.navigate(['/details', prescription.id]);
  }

  goToForm(): void {
    this.router.navigate(['/prescription-form']);
  }
}
