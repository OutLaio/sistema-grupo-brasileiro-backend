package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.OtherItemView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.StationeryTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.AgencyBoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CityView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.CalendarTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.GiftTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.PrintingShirtTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.StampView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.HandoutTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.PrintedTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.PrintingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.MaterialView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view.StickerInformationTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view.StickerTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.profile.view.ProfileView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.AgencyBoardTypeViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.BoardTypeViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.CityViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.CompanyViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.view.CalendarTypeViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.view.GiftTypeViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.view.PrintingShirtTypeViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.view.StampViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.view.HandoutTypeViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.internalCampaigns.view.OtherItemViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.internalCampaigns.view.StationeryTypeViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.printed.view.PrintedTypeViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.printed.view.PrintingTypeViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view.MaterialViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.sticker.view.StickerInformationTypeViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.sticker.view.StickerTypeViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.BriefingTypeViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view.ProfileViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.AgencyBoardTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.BoardTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.CityRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.CompanyRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.campaingInternal.OtherItemRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.campaingInternal.StationeryTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.CalendarTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.GiftTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.PrintingShirtTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.StampRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.handout.HandoutTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.printeds.PrintedTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.printeds.PrintingTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.singpost.MaterialRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.sticker.StickerInformationTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.sticker.StickerTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DataService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileViewMapper profileViewMapper;

    @Autowired
    private BriefingTypeRepository briefingTypeRepository;

    @Autowired
    private BriefingTypeViewMapper briefingTypeViewMapper;

    @Autowired
    private PrintedTypeRepository printedTypeRepository;

    @Autowired
    private PrintedTypeViewMapper printedTypeViewMapper;

    @Autowired
    private PrintingTypeRepository printingTypeRepository;

    @Autowired
    private PrintingTypeViewMapper printingTypeViewMapper;

    @Autowired
    private PrintingShirtTypeRepository printingShirtTypeRepository;

    @Autowired
    private PrintingShirtTypeViewMapper printingShirtTypeViewMapper;

    @Autowired
    private StampRepository stampRepository;

    @Autowired
    private StampViewMapper stampViewMapper;

    @Autowired
    private CalendarTypeRepository calendarTypeRepository;

    @Autowired
    private CalendarTypeViewMapper calendarTypeViewMapper;

    @Autowired
    private GiftTypeRepository giftTypeRepository;

    @Autowired
    private GiftTypeViewMapper giftTypeViewMapper;

    @Autowired
    private AgencyBoardTypeRepository agencyBoardTypeRepository;

    @Autowired
    private AgencyBoardTypeViewMapper agencyBoardTypeViewMapper;

    @Autowired
    private BoardTypeRepository boardTypeRepository;

    @Autowired
    private BoardTypeViewMapper boardTypeViewMapper;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityViewMapper cityViewMapper;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyViewMapper companyViewMapper;

    @Autowired
    private StickerTypeRepository stickerTypeRepository;

    @Autowired
    private StickerTypeViewMapper stickerTypeViewMapper;

    @Autowired
    private StickerInformationTypeRepository stickerInformationTypeRepository;

    @Autowired
    private StickerInformationTypeViewMapper stickerInformationTypeViewMapper;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialViewMapper materialViewMapper;

    @Autowired
    private HandoutTypeRepository handoutTypeRepository;

    @Autowired
    private HandoutTypeViewMapper handoutTypeViewMapper;

    @Autowired
    private StationeryTypeRepository stationeryTypeRepository;

    @Autowired
    private StationeryTypeViewMapper stationeryTypeViewMapper;

    @Autowired
    private OtherItemRepository otherItemRepository;

    @Autowired
    private OtherItemViewMapper otherItemViewMapper;

    public Set<ProfileView> getProfiles() {
        return profileRepository.findAll().stream().map(profileViewMapper::map).collect(Collectors.toSet());
    }

    public Set<BriefingTypeView> getBriefingTypes() {
        return briefingTypeRepository.findAll().stream().map(briefingTypeViewMapper::map).collect(Collectors.toSet());
    }

    public Set<PrintedTypeView> getPrintedTypes() {
        return printedTypeRepository.findAll().stream().map(printedTypeViewMapper::map).collect(Collectors.toSet());
    }

    public Set<PrintingTypeView> getPrintingTypes() {
        return printingTypeRepository.findAll().stream().map(printingTypeViewMapper::map).collect(Collectors.toSet());
    }

    public Set<PrintingShirtTypeView> getPrintingShirtTypes() {
        return printingShirtTypeRepository.findAll().stream().map(printingShirtTypeViewMapper::map).collect(Collectors.toSet());
    }

    public Set<StampView> getStamps() {
        return stampRepository.findAll().stream().map(stampViewMapper::map).collect(Collectors.toSet());
    }

    public Set<CalendarTypeView> getCalendarTypes() {
        return calendarTypeRepository.findAll().stream().map(calendarTypeViewMapper::map).collect(Collectors.toSet());
    }

    public Set<GiftTypeView> getGiftTypes() {
        return giftTypeRepository.findAll().stream().map(giftTypeViewMapper::map).collect(Collectors.toSet());
    }

    public Set<AgencyBoardTypeView> getAgencyBoardTypes() {
        return agencyBoardTypeRepository.findAll().stream().map(agencyBoardTypeViewMapper::map).collect(Collectors.toSet());
    }

    public Set<BoardTypeView> getBoardTypes() {
        return boardTypeRepository.findAll().stream().map(boardTypeViewMapper::map).collect(Collectors.toSet());
    }

    public Set<CityView> getCities() {
        return cityRepository.findAll().stream().map(cityViewMapper::map).collect(Collectors.toSet());
    }

    public Set<CompanyView> getCompanies() {
        return companyRepository.findAll().stream().map(companyViewMapper::map).collect(Collectors.toSet());
    }

    public Set<StickerTypeView> getStickerTypes() {
        return stickerTypeRepository.findAll().stream().map(stickerTypeViewMapper::map).collect(Collectors.toSet());
    }

    public Set<StickerInformationTypeView> getStickerInformationTypes() {
        return stickerInformationTypeRepository.findAll().stream().map(stickerInformationTypeViewMapper::map).collect(Collectors.toSet());
    }

    public Set<MaterialView> getMaterials() {
        return materialRepository.findAll().stream().map(materialViewMapper::map).collect(Collectors.toSet());
    }

    public Set<HandoutTypeView> getHandoutTypes() {
        return handoutTypeRepository.findAll().stream().map(handoutTypeViewMapper::map).collect(Collectors.toSet());
    }

    public Set<StationeryTypeView> getStationeryTypes() {
        return stationeryTypeRepository.findAll().stream().map(stationeryTypeViewMapper::map).collect(Collectors.toSet());
    }

    public Set<OtherItemView> getOtherItems() {
        return otherItemRepository.findAll().stream().map(otherItemViewMapper::map).collect(Collectors.toSet());
    }
}
