import { ComponentFixture, TestBed } from '@angular/core/testing';

import {  AdminManageProductsComponent } from './admin-manage-products';

describe('AdminManageProducts', () => {
  let component: AdminManageProductsComponent;
  let fixture: ComponentFixture<AdminManageProductsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminManageProductsComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(AdminManageProductsComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
