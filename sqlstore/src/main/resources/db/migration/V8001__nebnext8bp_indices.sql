INSERT INTO IndexFamily (name, platformType) VALUES ('NEBNext8bp', 'ILLUMINA');

SELECT indexFamilyId INTO @familyId FROM IndexFamily WHERE name = 'NEBNext8bp';
INSERT INTO Indices (name, sequence, position, indexFamilyId) VALUES
('01_A1', 'TTACCGAC', 1, @familyId),
('02_A2', 'AGTGACCT', 1, @familyId),
('03_A3', 'TCGGATTC', 1, @familyId),
('04_A4', 'CAAGGTAC', 1, @familyId),
('05_A5', 'TCCTCATG', 1, @familyId),
('06_A6', 'GTCAGTCA', 1, @familyId),
('07_A7', 'CGAATACG', 1, @familyId),
('08_A8', 'TCTAGGAG', 1, @familyId),
('09_A9', 'CGCAACTA', 1, @familyId),
('10_A10', 'CGTATCTC', 1, @familyId),
('11_A11', 'GTACACCT', 1, @familyId),
('12_A12', 'CGGCATTA', 1, @familyId),
('13_B1', 'TCGTCTGA', 1, @familyId),
('14_B2', 'AGCCTATC', 1, @familyId),
('15_B3', 'CTGTACCA', 1, @familyId),
('16_B4', 'AGACCTTG', 1, @familyId),
('17_B5', 'AGGATAGC', 1, @familyId),
('18_B6', 'CCTTCCAT', 1, @familyId),
('19_B7', 'GTCCTTGA', 1, @familyId),
('20_B8', 'TGCGTAAC', 1, @familyId),
('21_B9', 'CACAGACT', 1, @familyId),
('22_B10', 'TTACGTGC', 1, @familyId),
('23_B11', 'CCAAGGTT', 1, @familyId),
('24_B12', 'CACGCAAT', 1, @familyId),
('25_C1', 'TTCCAGGT', 1, @familyId),
('26_C2', 'TCATCTCC', 1, @familyId),
('27_C3', 'GAGAGTAC', 1, @familyId),
('28_C4', 'GTCGTTAC', 1, @familyId),
('29_C5', 'GGAGGAAT', 1, @familyId),
('30_C6', 'AGGAACAC', 1, @familyId),
('31_C7', 'CAGTGCTT', 1, @familyId),
('32_C8', 'CTTGCTAG', 1, @familyId),
('33_C9', 'TGGAAGCA', 1, @familyId),
('34_C10', 'AGCTAAGC', 1, @familyId),
('35_C11', 'GAACGGTT', 1, @familyId),
('36_C12', 'GGAATGTC', 1, @familyId),
('37_D1', 'TACGGTCT', 1, @familyId),
('38_D2', 'CCAGTATC', 1, @familyId),
('39_D3', 'TCTACGCA', 1, @familyId),
('40_D4', 'GTAACCGA', 1, @familyId),
('41_D5', 'GACGTCAT', 1, @familyId),
('42_D6', 'CTTACAGC', 1, @familyId),
('43_D7', 'TCCATTGC', 1, @familyId),
('44_D8', 'AGCGAGAT', 1, @familyId),
('45_D9', 'CAATAGCC', 1, @familyId),
('46_D10', 'AAGACACC', 1, @familyId),
('47_D11', 'CCAGTTGA', 1, @familyId),
('48_D12', 'TGGTGAAG', 1, @familyId),
('49_E1', 'AAGACCGT', 1, @familyId),
('50_E2', 'TTGCGAGA', 1, @familyId),
('51_E3', 'GCAATTCC', 1, @familyId),
('52_E4', 'GAATCCGT', 1, @familyId),
('53_E5', 'CCGCTTAA', 1, @familyId),
('54_E6', 'TACCTGCA', 1, @familyId),
('55_E7', 'GTCGATTG', 1, @familyId),
('56_E8', 'TATGGCAC', 1, @familyId),
('57_E9', 'CTCGAACA', 1, @familyId),
('58_E10', 'CAACTCCA', 1, @familyId),
('59_E11', 'GTCATCGT', 1, @familyId),
('60_E12', 'GGACATCA', 1, @familyId),
('61_F1', 'CAGGTTCA', 1, @familyId),
('62_F2', 'GAACGAAG', 1, @familyId),
('63_F3', 'CTCAGAAG', 1, @familyId),
('64_F4', 'CATGAGCA', 1, @familyId),
('65_F5', 'GACGAACT', 1, @familyId),
('66_F6', 'AGACGCTA', 1, @familyId),
('67_F7', 'ATAACGCC', 1, @familyId),
('68_F8', 'GAATCACC', 1, @familyId),
('69_F9', 'GGCAAGTT', 1, @familyId),
('70_F10', 'GATCTTGC', 1, @familyId),
('71_F11', 'CAATGCGA', 1, @familyId),
('72_F12', 'GGTGTACA', 1, @familyId),
('73_G1', 'TAGGAGCT', 1, @familyId),
('74_G2', 'CGAATTGC', 1, @familyId),
('75_G3', 'GTCCTAAG', 1, @familyId),
('76_G4', 'CTTAGGAC', 1, @familyId),
('77_G5', 'TCCACGTT', 1, @familyId),
('78_G6', 'CAACACAG', 1, @familyId),
('79_G7', 'GCCTTAAC', 1, @familyId),
('80_G8', 'GTAAGGTG', 1, @familyId),
('81_G9', 'AGCTACCA', 1, @familyId),
('82_G10', 'CTTCACTG', 1, @familyId),
('83_G11', 'GGTTGAAC', 1, @familyId),
('84_G12', 'GATAGCCA', 1, @familyId),
('85_H1', 'TACTCCAG', 1, @familyId),
('86_H2', 'GGAAGAGA', 1, @familyId),
('87_H3', 'GCGTTAGA', 1, @familyId),
('88_H4', 'ATCTGACC', 1, @familyId),
('89_H5', 'AACCAGAG', 1, @familyId),
('90_H6', 'GTACCACA', 1, @familyId),
('91_H7', 'GGTATAGG', 1, @familyId),
('92_H8', 'CGAGAGAA', 1, @familyId),
('93_H9', 'CAGCATAC', 1, @familyId),
('94_H10', 'CTCGACTT', 1, @familyId),
('95_H11', 'CTTCGGTT', 1, @familyId),
('96_H12', 'CCACAACA', 1, @familyId);
