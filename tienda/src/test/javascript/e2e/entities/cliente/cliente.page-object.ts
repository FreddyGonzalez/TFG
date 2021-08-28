import { element, by, ElementFinder } from 'protractor';

export class ClienteComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-cliente div table .btn-danger'));
  title = element.all(by.css('jhi-cliente div h2#page-heading span')).first();
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

export class ClienteUpdatePage {
  pageTitle = element(by.id('jhi-cliente-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idClienteInput = element(by.id('field_idCliente'));
  nombreInput = element(by.id('field_nombre'));
  apellidoInput = element(by.id('field_apellido'));
  generoSelect = element(by.id('field_genero'));
  emailInput = element(by.id('field_email'));
  telefonoInput = element(by.id('field_telefono'));
  direccionInput = element(by.id('field_direccion'));
  ciudadInput = element(by.id('field_ciudad'));
  paisInput = element(by.id('field_pais'));

  userSelect = element(by.id('field_user'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdClienteInput(idCliente: string): Promise<void> {
    await this.idClienteInput.sendKeys(idCliente);
  }

  async getIdClienteInput(): Promise<string> {
    return await this.idClienteInput.getAttribute('value');
  }

  async setNombreInput(nombre: string): Promise<void> {
    await this.nombreInput.sendKeys(nombre);
  }

  async getNombreInput(): Promise<string> {
    return await this.nombreInput.getAttribute('value');
  }

  async setApellidoInput(apellido: string): Promise<void> {
    await this.apellidoInput.sendKeys(apellido);
  }

  async getApellidoInput(): Promise<string> {
    return await this.apellidoInput.getAttribute('value');
  }

  async setGeneroSelect(genero: string): Promise<void> {
    await this.generoSelect.sendKeys(genero);
  }

  async getGeneroSelect(): Promise<string> {
    return await this.generoSelect.element(by.css('option:checked')).getText();
  }

  async generoSelectLastOption(): Promise<void> {
    await this.generoSelect.all(by.tagName('option')).last().click();
  }

  async setEmailInput(email: string): Promise<void> {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput(): Promise<string> {
    return await this.emailInput.getAttribute('value');
  }

  async setTelefonoInput(telefono: string): Promise<void> {
    await this.telefonoInput.sendKeys(telefono);
  }

  async getTelefonoInput(): Promise<string> {
    return await this.telefonoInput.getAttribute('value');
  }

  async setDireccionInput(direccion: string): Promise<void> {
    await this.direccionInput.sendKeys(direccion);
  }

  async getDireccionInput(): Promise<string> {
    return await this.direccionInput.getAttribute('value');
  }

  async setCiudadInput(ciudad: string): Promise<void> {
    await this.ciudadInput.sendKeys(ciudad);
  }

  async getCiudadInput(): Promise<string> {
    return await this.ciudadInput.getAttribute('value');
  }

  async setPaisInput(pais: string): Promise<void> {
    await this.paisInput.sendKeys(pais);
  }

  async getPaisInput(): Promise<string> {
    return await this.paisInput.getAttribute('value');
  }

  async userSelectLastOption(): Promise<void> {
    await this.userSelect.all(by.tagName('option')).last().click();
  }

  async userSelectOption(option: string): Promise<void> {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption(): Promise<string> {
    return await this.userSelect.element(by.css('option:checked')).getText();
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

export class ClienteDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-cliente-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-cliente'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
