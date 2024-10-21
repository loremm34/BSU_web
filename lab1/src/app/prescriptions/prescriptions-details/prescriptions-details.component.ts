import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PrescriptionService } from '../services/prescriptions.service';
import { Prescriptions } from '../services/mock-prescriptions-list';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-prescriptions-details',
  templateUrl: './prescriptions-details.component.html',
  styleUrls: ['./prescriptions-details.component.sass'],
})
export class PrescriptionsDetailsComponent implements OnInit {
  prescription?: Prescriptions;

  constructor(
    private route: ActivatedRoute,
    private prescriptionsService: PrescriptionService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.prescriptionsService
        .getPrescriptionss()
        .subscribe((prescriptions) => {
          this.prescription = prescriptions.find((p) => p.id === id);

          if (!this.prescription) {
            console.error('Prescription not found!');
          }
        });
    }
  }
}
