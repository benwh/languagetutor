PRAGMA foreign_keys = ON;

-- -----------------------------------------------------
-- Table `profile`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `profile` (
  `profileID` INT NOT NULL ,
  `display_name` VARCHAR(30) NOT NULL ,
  `password_hash` VARCHAR(64) NOT NULL ,
  `secret_q` VARCHAR(255) NULL ,
  `secret_a` VARCHAR(30) NULL ,
  `theme` TINYINT NULL ,
  PRIMARY KEY (`profileID`) );


-- -----------------------------------------------------
-- Table `langentity`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `langentity` (
  `entityID` INT NOT NULL ,
  `phrase` TINYINT(1) NOT NULL ,
  `phrase_partial` TINYINT(1) NOT NULL ,
  `source_text` VARCHAR(255) NOT NULL ,
  `dest_text` VARCHAR(255) NOT NULL ,
  `audio_asset` TINYINT(1) NULL ,
  `image_asset` VARCHAR(45) NULL ,
  PRIMARY KEY (`entityID`) );


-- -----------------------------------------------------
-- Table `langset`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `langset` (
  `setID` INT NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `level` TINYINT NOT NULL ,
  `locked` TINYINT(1) NOT NULL ,
  `displayable` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`setID`) );


-- -----------------------------------------------------
-- Table `entity_set`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `entity_set` (
  `entityID` INT NOT NULL ,
  `setID` INT NOT NULL ,
  PRIMARY KEY (`entityID`, `setID`) ,
  CONSTRAINT `fk_entityID`
    FOREIGN KEY (`entityID` )
    REFERENCES `langentity` (`entityID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_setID`
    FOREIGN KEY (`setID` )
    REFERENCES `langset` (`setID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `entity_progress`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `entity_progress` (
  `profileID` INT NOT NULL ,
  `entityID` INT NOT NULL ,
  `score` SMALLINT NOT NULL DEFAULT 0 ,
  `hits` SMALLINT NOT NULL DEFAULT 0 ,
  `last_attempt` DATETIME NULL ,
  PRIMARY KEY (`profileID`, `entityID`) ,
  CONSTRAINT `fk_profileID`
    FOREIGN KEY (`profileID` )
    REFERENCES `profile` (`profileID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_entityID`
    FOREIGN KEY (`entityID` )
    REFERENCES `langentity` (`entityID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `test_results`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `test_results` (
  `profileID` INT NOT NULL ,
  `langsetID` INT NOT NULL ,
  `time` DATETIME NOT NULL ,
  `score` TINYINT NOT NULL ,
  PRIMARY KEY (`profileID`, `langsetID`, `time`) ,
  CONSTRAINT `fk_profileID`
    FOREIGN KEY (`profileID` )
    REFERENCES `profile` (`profileID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_setID`
    FOREIGN KEY (`langsetID` )
    REFERENCES `langset` (`setID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `game_results`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `game_results` (
  `profileID` INT NOT NULL ,
  `langsetID` INT NOT NULL ,
  `time` DATETIME NOT NULL ,
  `points` INT NOT NULL ,
  PRIMARY KEY (`profileID`, `langsetID`, `time`) ,
  CONSTRAINT `fk_profileID`
    FOREIGN KEY (`profileID` )
    REFERENCES `profile` (`profileID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_setID`
    FOREIGN KEY (`langsetID` )
    REFERENCES `langset` (`setID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `phrase_completions`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `phrase_completions` (
  `entityID` INT NOT NULL ,
  `setID` INT NOT NULL ,
  PRIMARY KEY (`entityID`, `setID`) ,
  CONSTRAINT `fk_entityID`
    FOREIGN KEY (`entityID` )
    REFERENCES `langentity` (`entityID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_setID`
    FOREIGN KEY (`setID` )
    REFERENCES `langset` (`setID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
