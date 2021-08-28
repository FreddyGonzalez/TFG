import { element, by, ElementFinder } from 'protractor';

export class ProductoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-producto div table .btn-danger'));
  title = element.all(by.css('jhi-producto div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class ProductoUpdatePage {
  pageTitle = element(by.id('jhi-producto-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nombreInput = element(by.id('field_nombre'));
  descripcionInput = element(by.id('field_descripcion'));
  precioInput = element(by.id('field_precio'));
  tallaSelect = element(by.id('field_talla'));
  imagenInput = element(by.id('file_imagen'));

  categoriaSelect = element(by.id('field_categoria'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNombreInput(nombre: string): Promise<void> {
    await this.nombreInput.sendKeys(nombre);
  }

  async getNombreInput(): Promise<string> {
    return await this.nombreInput.getAttribute('value');
  }

  async setDescripcionInput(descripcion: string): Promise<void> {
    await this.descripcionInput.sendKeys(descripcion);
  }

  async getDescripcionInput(): Promise<string> {
    return await this.descripcionInput.getAttribute('value');
  }

  async setPrecioInput(precio: string): Promise<void> {
    await this.precioInput.sendKeys(precio);
  }

  async getPrecioInput(): Promise<string> {
    return await this.precioInput.getAttribute('value');
  }

  async setTallaSelect(talla: string): Promise<void> {
    await this.tallaSelect.sendKeys(talla);
  }

  async getTallaSelect(): Promise<string> {
    return await this.tallaSelect.element(by.css('option:checked')).getText();
  }

  async tallaSelectLastOption(): Promise<void> {
    await this.tallaSelect.all(by.tagName('option')).last().click();
  }

  async setImagenInput(imagen: string): Promise<void> {
    await this.imagenInput.sendKeys(imagen);
  }

  async getImagenInput(): Promise<string> {
    return await this.imagenInput.getAttribute('value');
  }

  async categoriaSelectLastOption(): Promise<void> {
    await this.categoriaSelect.all(by.tagName('option')).last().click();
  }

  async categoriaSelectOption(option: string): Promise<void> {
    await this.categoriaSelect.sendKeys(option);
  }

  getCategoriaSelect(): ElementFinder {
    return this.categoriaSelect;
  }

  async getCategoriaSelectedOption(): Promise<string> {
    return await this.categoriaSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ProductoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-producto-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-producto'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
