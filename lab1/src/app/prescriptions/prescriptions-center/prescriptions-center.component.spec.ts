import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrescriptionsCenterComponent } from './prescriptions-center.component';

describe('PreprescriptionsCenterComponent', () => {
  let component: PrescriptionsCenterComponent;
  let fixture: ComponentFixture<PrescriptionsCenterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PrescriptionsCenterComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(PrescriptionsCenterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
