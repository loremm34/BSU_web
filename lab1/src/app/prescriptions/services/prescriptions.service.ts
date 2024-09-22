import { Injectable } from '@angular/core';
import { AngularFirestore } from '@angular/fire/compat/firestore';
import { Observable } from 'rxjs';
import { Prescriptions } from './mock-prescriptions-list';

@Injectable({
  providedIn: 'root',
})
export class PrescriptionService {
  private collectionName = 'prescription-list';

  constructor(private firestore: AngularFirestore) {}

  getPrescriptionss(): Observable<Prescriptions[]> {
    return this.firestore
      .collection<Prescriptions>(this.collectionName)
      .valueChanges({ idField: 'id' });
  }

  addPrescriptions(prescription: Prescriptions): Promise<void> {
    const id = this.firestore.createId();
    return this.firestore
      .collection(this.collectionName)
      .doc(id)
      .set({ ...prescription, id });
  }

  updatePrescriptions(prescription: Prescriptions): Promise<void> {
    return this.firestore
      .collection(this.collectionName)
      .doc(prescription.id)
      .update(prescription);
  }

  deletePrescriptions(prescriptionId: string): Promise<void> {
    return this.firestore
      .collection(this.collectionName)
      .doc(prescriptionId)
      .delete();
  }

  loadMockPrescriptions(mockPrescriptions: Prescriptions[]): Promise<void> {
    const batch = this.firestore.firestore.batch();
    const prescriptionsRef = this.firestore.collection(this.collectionName).ref;

    mockPrescriptions.forEach((prescription) => {
      const docRef = prescriptionsRef.doc(prescription.id.toString());
      batch.set(docRef, prescription);
    });

    return batch.commit();
  }
}
