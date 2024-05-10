package com.example.facebookclone.controller;

import com.example.facebookclone.DTO.PostDTO;
import com.example.facebookclone.DTO.ShareDTO;
import com.example.facebookclone.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/facebook.api/shares")
@CrossOrigin
public class ShareController {
    @Autowired
    private ShareService shareService;
    @GetMapping("/{id}")
    public List<ShareDTO> getSharesByAccountId(@PathVariable int id) {
        return shareService.getSharesByAccountId(id);
    }

    @PostMapping(value = "/createShare")
    public ShareDTO createShare(
            @RequestParam(name = "id_account") Integer id_account,
            @RequestParam(name = "content", required = false) String content,
            @RequestParam(name = "view_mode") String view_mode,
            @RequestParam(name = "id_post") Integer id_post
    ) {
        return shareService.saveShare(id_account, content, view_mode, id_post);
    }

    @PatchMapping(value = "/updateShare")
    public ShareDTO updateShare(
            @RequestParam(name = "id_share") Integer id_share,
            @RequestParam(name = "content", required = false) String content,
            @RequestParam(name = "view_mode", required = false) String view_mode
    ){
        return shareService.updateShare(id_share, content, view_mode);
    }
    @DeleteMapping(value = "/deleteShare/{id}")
    public ResponseEntity<String> deleteShare(@PathVariable int id) {
        shareService.deleteShare(id);
        return ResponseEntity.ok("Delete Share successfully");
    }
}
