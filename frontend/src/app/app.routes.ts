import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login';
import { RegisterComponent } from './pages/register/register';
import { DashboardComponent } from './pages/dashboard/dashboard';
import { AdminAddProductComponent } from './pages/admin-add-product/admin-add-product';
import { AdminManageProductsComponent } from './pages/admin-manage-products/admin-manage-products';

export const routes: Routes = [
    { path: '', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'admin/add-product', component: AdminAddProductComponent },
  { path: 'admin/manage-products', component: AdminManageProductsComponent }
];
