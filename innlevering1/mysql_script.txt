CREATE TABLE Økt (
    ØktID INT NOT NULL AUTO_INCREMENT,
    Dato DATE,
    Tidspunkt TIME,
    Varighet INT,
    Form INT,
    Prestasjon INT,
    Notat VARCHAR(200),
    CONSTRAINT Økt_PK PRIMARY KEY (ØktID),
    CONSTRAINT FormSjekk CHECK (Form <= 10 AND Form >= 1),
    CONSTRAINT PrestasjonSjekk CHECK (Prestasjon <= 10 AND Prestasjon >= 1)
);
    
CREATE TABLE Øvelse (
    ØvelseID INT NOT NULL AUTO_INCREMENT,
    Navn VARCHAR(100),
    CONSTRAINT Øvelse_PK PRIMARY KEY (ØvelseID)
);
    
CREATE TABLE ØktHarØvelse (
    ØktID INT NOT NULL,
    ØvelseID INT NOT NULL,
    CONSTRAINT ØktHarØvelse_PK PRIMARY KEY (ØktID , ØvelseID),
    CONSTRAINT ØktHarØvelse_FK1 FOREIGN KEY (ØktID)
        REFERENCES Økt (ØktID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT ØktHarØvelse_FK2 FOREIGN KEY (ØvelseID)
        REFERENCES Øvelse (ØvelseID)
        ON DELETE CASCADE ON UPDATE CASCADE
);
    
CREATE TABLE Øvelsesgruppe (
    ØvelsesgruppeID INT NOT NULL AUTO_INCREMENT,
    Navn VARCHAR(100),
    PRIMARY KEY (ØvelsesgruppeID)
);

CREATE TABLE ØvelseIGruppe (
    ØvelsesgruppeID INT NOT NULL,
    ØvelseID INT NOT NULL,
    CONSTRAINT ØvelseIGruppe_PK PRIMARY KEY (ØvelsesgruppeID , ØvelseID),
    CONSTRAINT ØvelseIGruppe_FK1 FOREIGN KEY (ØvelsesgruppeID)
        REFERENCES Øvelsesgruppe (ØvelsesgruppeID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT ØvelseIGruppe_FK2 FOREIGN KEY (ØvelseID)
        REFERENCES Øvelse (ØvelseID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Apparat (
    ApparatID INT NOT NULL AUTO_INCREMENT,
    Navn VARCHAR(100),
    Beskrivelse VARCHAR(240),
    CONSTRAINT Apparat_PK PRIMARY KEY (ApparatID)
);

CREATE TABLE ØvelseMedApparat (
    ØvelseID INT NOT NULL,
    ApparatID INT NOT NULL,
    AntallKilo INT,
    AntallSet INT,
    CONSTRAINT ØvelseMedApparat_PK PRIMARY KEY (ØvelseID),
    CONSTRAINT ØvelseMedApparat_FK1 FOREIGN KEY (ØvelseID)
        REFERENCES Øvelse (ØvelseID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT ØvelseMedApparat_FK2 FOREIGN KEY (ApparatId)
        REFERENCES Apparat (ApparatID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ØvelseUtenApparat (
    ØvelseID INT NOT NULL,
    Beskrivelse VARCHAR(200),
    CONSTRAINT ØvelseUtenApparat_PK PRIMARY KEY (ØvelseID),
    CONSTRAINT ØvelseUtenApparat_FK1 FOREIGN KEY (ØvelseID)
        REFERENCES Øvelse (ØvelseID)
        ON DELETE CASCADE ON UPDATE CASCADE
);