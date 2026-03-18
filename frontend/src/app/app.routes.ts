import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login';
import { RegisterComponent } from './pages/register/register';
import { DashboardComponent } from './pages/dashboard/dashboard';
import { AdminAddProductComponent } from './pages/admin-add-product/admin-add-product';
import { AdminManageProductsComponent } from './pages/admin-manage-products/admin-manage-products';
import {  AdminEditProductComponent } from './pages/admin-edit-product/admin-edit-product';
import { AdminGuard } from './services/admin.guard';
import { AuthGuard } from './services/auth.guard';

export const routes: Routes = [
    { path: '', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'admin/add-product', component: AdminAddProductComponent, canActivate: [AdminGuard] },
  { path: 'admin/manage-products', component: AdminManageProductsComponent, canActivate: [AdminGuard] },
  { path: 'admin/edit-product/:id', component: AdminEditProductComponent, canActivate: [AdminGuard] }
];
